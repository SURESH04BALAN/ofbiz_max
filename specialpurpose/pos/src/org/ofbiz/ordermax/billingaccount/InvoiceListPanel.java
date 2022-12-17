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
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.InvoiceCompositeTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.composite.BillingAccountComposite;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class InvoiceListPanel extends javax.swing.JPanel {

    private final JTextField txtProdIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;

//    private InvoiceCompositeTableModel paymentTableModel = new InvoiceCompositeTableModel();
//    private ListAdapterListModel<InvoiceComposite> invoiceCompositeListModel = new ListAdapterListModel<InvoiceComposite>();
    public GenericTableModelPanel<InvoiceComposite, InvoiceCompositeTableModel> tablePanel = null;
    ControllerOptions options = null;

    /**
     * Creates new form ReceiveInventoryPanel
     */
    public InvoiceListPanel(ControllerOptions options) {
        initComponents();
        this.options = options;

        tablePanel = new GenericTableModelPanel<InvoiceComposite, InvoiceCompositeTableModel>(new InvoiceCompositeTableModel());
        panelIResultList.setLayout(new BorderLayout());
        panelIResultList.add(BorderLayout.CENTER, tablePanel);
        setupEditOrderTable();

        ComponentBorder.doubleRaisedLoweredBevelBorder(panelIResultList, "Invoice List");
    }
    BillingAccountComposite billingAccountComposite = null;

    public void setBillingAccountComposite(BillingAccountComposite billingAccountComposite) {
        this.billingAccountComposite = billingAccountComposite;
        billingAccountInvoices = billingAccountComposite.getBillingAccountInvoices();
    }

    public void clearDialogFields() {
        if (billingAccountInvoices != null) {
            billingAccountInvoices.clear();
        }
    }

    public void setDialogFields() {
        if (billingAccountInvoices != null) {
            tablePanel.setListModel(billingAccountInvoices);
        }
    }

    public void getDialogFields() {

    }
    ListAdapterListModel<InvoiceComposite> billingAccountInvoices = null;

    public InvoiceComposite getSelectedInvoice() {
        return tablePanel.listModelSelection.getSelection();
    }

    public JTextField getTxtProdIdTableTextField() {
        return txtProdIdTableTextField;
    }

    public ProductTreeActionTableCellEditor getProductTreeActionTableCellEditor() {
        return productTreeActionTableCellEditor;
    }

    final public void setupEditOrderTable() {

//        tableReceiveInv.setSelectAllForEdit(true);
        tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
        txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
        editor.setClickCountToStart(0);
        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);
        tablePanel.jTable.getColumn("Invoice Id").setCellEditor(productTreeActionTableCellEditor);

        for (int i = 0; i < InvoiceCompositeTableModel.Columns.values().length; i++) {
            InvoiceCompositeTableModel.Columns[] columns = InvoiceCompositeTableModel.Columns.values();
            InvoiceCompositeTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());
            }
        }

    }

    static class DecimalFormatRenderer extends DefaultTableCellRenderer {

        private static final DecimalFormat formatter = new DecimalFormat("#.00");

        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            BigDecimal bdValue = (BigDecimal) value;
            value = bdValue.setScale(org.ofbiz.order.shoppingcart.ShoppingCart.scale, org.ofbiz.order.shoppingcart.ShoppingCart.rounding);
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

        panelIResultList = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        panelIResultList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Invoice List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        javax.swing.GroupLayout panelIResultListLayout = new javax.swing.GroupLayout(panelIResultList);
        panelIResultList.setLayout(panelIResultListLayout);
        panelIResultListLayout.setHorizontalGroup(
            panelIResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1281, Short.MAX_VALUE)
        );
        panelIResultListLayout.setVerticalGroup(
            panelIResultListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        add(panelIResultList, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panelIResultList;
    // End of variables declaration//GEN-END:variables
}
