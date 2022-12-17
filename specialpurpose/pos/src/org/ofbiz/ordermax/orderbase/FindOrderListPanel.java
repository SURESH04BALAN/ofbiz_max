/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.OrderTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.utility.ComponentBorder;
import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class FindOrderListPanel extends javax.swing.JPanel {

    public OrderTableModel receiveInventoryTableModel = new OrderTableModel();
    private final JTextField txtProdIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;
    ControllerOptions controllerOptions = null;

    /**
     * Creates new form ReceiveInventoryPanel
     */
    public FindOrderListPanel(ControllerOptions controllerOptions) {
        initComponents();
        tableReceiveInv.setModel(receiveInventoryTableModel);
        setupEditOrderTable();
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelLookupOrder, "Lookup Order(s)");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelOrderList, "Order List");
    }

    public void setReceiveInventoryList(ListAdapterListModel<Order> orderListModel) {

        receiveInventoryTableModel.setListModel(orderListModel);
    }

    public JTextField getTxtProdIdTableTextField() {
        return txtProdIdTableTextField;
    }

    public ProductTreeActionTableCellEditor getProductTreeActionTableCellEditor() {
        return productTreeActionTableCellEditor;
    }

    final public void setupEditOrderTable() {

//        tableReceiveInv.setSelectAllForEdit(true);
        tableReceiveInv.setSurrendersFocusOnKeystroke(true);
        txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
        editor.setClickCountToStart(0);
        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);
        tableReceiveInv.getColumn("Order Nbr").setCellEditor(productTreeActionTableCellEditor);
        String iconPath = "C:\\AuthLog\\ofbiz-12.04.02\\question image_16.jpg";
        /*        ImageIcon icon = new ImageIcon(OrderMaxUtility.getImage(iconPath),"...");
         if (icon != null) {
         productTreeActionTableCellEditor.getCustomEditorButton().setIcon( icon);
         }
         */
//        column.setCellEditor(productTreeActionTableCellEditor);        
//        if (!orderEntryTableModel.hasEmptyRow()) {
//            orderEntryTableModel.addEmptyRow();
//        }
        for (int i = 0; i < OrderTableModel.Columns.values().length; i++) {
            OrderTableModel.Columns[] columns = OrderTableModel.Columns.values();
            OrderTableModel.Columns column = columns[i];
            TableColumn col = tableReceiveInv.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());
            }
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
        panelLookupOrder = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbAll = new javax.swing.JCheckBox();
        cbCreated = new javax.swing.JCheckBox();
        cbProcessing = new javax.swing.JCheckBox();
        cbApproved = new javax.swing.JCheckBox();
        cbHeld = new javax.swing.JCheckBox();
        cbCompleted = new javax.swing.JCheckBox();
        cbRejected = new javax.swing.JCheckBox();
        cbCanceled = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        cbSalesOrder = new javax.swing.JCheckBox();
        cbPurchaseOrder = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        cbInventoryProblem = new javax.swing.JCheckBox();
        cbAll4 = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        cbPartiallyReceived = new javax.swing.JCheckBox();
        cbOpenPastTheirETA = new javax.swing.JCheckBox();
        cbRejectedItems = new javax.swing.JCheckBox();
        btnFind = new javax.swing.JButton();
        btnChangeFont = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        panelOrderList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableReceiveInv = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        panelLookupOrder.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Lookup Order(s)", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        panelLookupOrder.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        panelLookupOrder.setPreferredSize(new java.awt.Dimension(10, 140));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Status:");

        cbAll.setText("All");
        cbAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbAllActionPerformed(evt);
            }
        });

        cbCreated.setSelected(true);
        cbCreated.setText("Created");

        cbProcessing.setSelected(true);
        cbProcessing.setText("Processing");

        cbApproved.setSelected(true);
        cbApproved.setText("Approved");
        cbApproved.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbApprovedActionPerformed(evt);
            }
        });

        cbHeld.setText("Held");

        cbCompleted.setText(" Completed");

        cbRejected.setText(" Rejected");

        cbCanceled.setText(" Canceled");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Type:");

        cbSalesOrder.setText("Sales Order");

        cbPurchaseOrder.setSelected(true);
        cbPurchaseOrder.setText("Purchase Order ");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Filter:");

        cbInventoryProblem.setText(" Inventory problems");

        cbAll4.setText(" Authorisation Problems ");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Filter (purchase orders):");

        cbPartiallyReceived.setText("partially received");

        cbOpenPastTheirETA.setText("open past their ETA");

        cbRejectedItems.setText(" with rejected items ");

        btnFind.setText("Find");

        btnChangeFont.setText("Change Font");

        javax.swing.GroupLayout panelLookupOrderLayout = new javax.swing.GroupLayout(panelLookupOrder);
        panelLookupOrder.setLayout(panelLookupOrderLayout);
        panelLookupOrderLayout.setHorizontalGroup(
            panelLookupOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLookupOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(panelLookupOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)))
                .addGap(10, 10, 10)
                .addGroup(panelLookupOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbAll)
                    .addComponent(cbSalesOrder)
                    .addComponent(cbInventoryProblem)
                    .addComponent(cbPartiallyReceived))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbCreated)
                    .addComponent(cbPurchaseOrder)
                    .addComponent(cbAll4)
                    .addComponent(cbOpenPastTheirETA))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelLookupOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLookupOrderLayout.createSequentialGroup()
                        .addComponent(cbProcessing)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbApproved))
                    .addComponent(cbRejectedItems))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbHeld, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFind, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(panelLookupOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLookupOrderLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCompleted)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbRejected)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbCanceled))
                    .addGroup(panelLookupOrderLayout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(btnChangeFont)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelLookupOrderLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel3, jLabel4, jLabel5});

        panelLookupOrderLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnFind, cbAll, cbApproved, cbCanceled, cbCompleted, cbCreated, cbHeld, cbProcessing, cbRejected});

        panelLookupOrderLayout.setVerticalGroup(
            panelLookupOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupOrderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLookupOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbAll)
                    .addComponent(cbCreated)
                    .addComponent(cbProcessing)
                    .addComponent(cbApproved)
                    .addComponent(cbHeld)
                    .addComponent(cbCompleted)
                    .addComponent(cbRejected)
                    .addComponent(cbCanceled))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbSalesOrder)
                    .addComponent(cbPurchaseOrder))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbInventoryProblem)
                    .addComponent(cbAll4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupOrderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cbPartiallyReceived)
                    .addComponent(cbOpenPastTheirETA)
                    .addComponent(cbRejectedItems)
                    .addComponent(btnFind)
                    .addComponent(btnChangeFont))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelLookupOrder, javax.swing.GroupLayout.PREFERRED_SIZE, 901, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLookupOrder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        panelOrderList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Order List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        panelOrderList.setLayout(new java.awt.GridLayout(1, 0));

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

        panelOrderList.add(jScrollPane1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelOrderList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelOrderList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(17, 17, 17))
        );

        add(jPanel3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void cbApprovedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbApprovedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbApprovedActionPerformed

    private void cbAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbAllActionPerformed

        cbApproved.setSelected(cbAll.isSelected());
        cbCanceled.setSelected(cbAll.isSelected());
        cbCompleted.setSelected(cbAll.isSelected());
        cbCreated.setSelected(cbAll.isSelected());
        cbHeld.setSelected(cbAll.isSelected());
        cbProcessing.setSelected(cbAll.isSelected());
        cbRejected.setSelected(cbAll.isSelected());

    }//GEN-LAST:event_cbAllActionPerformed
/*    
    private static class JTableButtonRenderer implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JButton button = (JButton) value;
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(UIManager.getColor("Button.background"));
            }
            return button;
        }
    }

    private static class JTableButtonMouseListener extends MouseAdapter {

        private final JTable table;

        public JTableButtonMouseListener(JTable table) {
            this.table = table;
        }

        public void mouseClicked(MouseEvent e) {

            int row = table.getSelectedRow();
            int column = table.getSelectedColumn();

//            int column = table.getColumnModel().getColumnIndexAtX(e.getX());
//            int row = e.getY() / table.getRowHeight();
            if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
                Object value = table.getValueAt(row, column);
                if (value instanceof JButton) {
                    ((JButton) value).doClick();
                }
            }
        }
    }
*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnChangeFont;
    public javax.swing.JButton btnFind;
    public javax.swing.JCheckBox cbAll;
    private javax.swing.JCheckBox cbAll4;
    public javax.swing.JCheckBox cbApproved;
    public javax.swing.JCheckBox cbCanceled;
    public javax.swing.JCheckBox cbCompleted;
    public javax.swing.JCheckBox cbCreated;
    public javax.swing.JCheckBox cbHeld;
    public javax.swing.JCheckBox cbInventoryProblem;
    public javax.swing.JCheckBox cbOpenPastTheirETA;
    public javax.swing.JCheckBox cbPartiallyReceived;
    public javax.swing.JCheckBox cbProcessing;
    public javax.swing.JCheckBox cbPurchaseOrder;
    public javax.swing.JCheckBox cbRejected;
    public javax.swing.JCheckBox cbRejectedItems;
    public javax.swing.JCheckBox cbSalesOrder;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelLookupOrder;
    private javax.swing.JPanel panelOrderList;
    public javax.swing.JTable tableReceiveInv;
    // End of variables declaration//GEN-END:variables
}