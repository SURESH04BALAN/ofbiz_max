/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns.receivereturn;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListComboBoxModel;
import mvc.model.list.OrderListCellRenderer;
import mvc.model.table.ReceiveInventoryTableModel;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.rounding;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.scale;
import org.ofbiz.ordermax.celleditors.DateTimeTableCellEditor;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.entity.InventoryItemType;
import org.ofbiz.ordermax.entity.RejectionReason;
import org.ofbiz.ordermax.inventory.InventoryItemTypeSingleton;
import org.ofbiz.ordermax.inventory.RejectionReasonSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class ReceiveReturnInventoryPanel extends javax.swing.JPanel {

    private ReceiveInventoryTableModel receiveInventoryTableModel = new ReceiveInventoryTableModel();
    public ListComboBoxModel<Order> orderComboBoxModel = new ListComboBoxModel<Order>();
    public JComboBox<Order> ordersComboBox = new JComboBox<Order>(orderComboBoxModel);
    public ListSelectionModel selectionModel = new DefaultListSelectionModel();

    /**
     * Creates new form ReceiveInventoryPanel
     */
    public ReceiveReturnInventoryPanel() {
        initComponents();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        ListCellRenderer<Order> orderRenderer = new OrderListCellRenderer();
        ordersComboBox.setRenderer(orderRenderer);
        orderComboBoxModel.setListSelectionModel(selectionModel);
        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(BorderLayout.CENTER, ordersComboBox);
        tableReceiveInv.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        tableReceiveInv.setSurrendersFocusOnKeystroke(true);
        tableReceiveInv.setRowHeight(20);
        tableReceiveInv.setSelectionBackground(Color.WHITE);
        tableReceiveInv.setSelectionForeground(Color.BLACK);
        tableReceiveInv.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableReceiveInv.getTableHeader().setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableReceiveInv.setModel(receiveInventoryTableModel);
        tableReceiveInv.setFillsViewportHeight(true);
        tableReceiveInv.setPreferredScrollableViewportSize(Toolkit.getDefaultToolkit().getScreenSize());

        setupEditOrderTable();
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelHeader, "Receive Order Header");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelDetails, "Receive Order Details");

    }

    public void setReceiveInventoryList(ListAdapterListModel<ShipmentReceiptComposite> orderListModel) {

        receiveInventoryTableModel.setListModel(orderListModel);
    }

    public void setOrderList(ListAdapterListModel<Order> orderListModel) {
        orderComboBoxModel.setListModel(orderListModel);
    }

    final public void setupEditOrderTable() {

//        tableReceiveInv.setSelectAllForEdit(true);
        tableReceiveInv.setSurrendersFocusOnKeystroke(true);

// Create our cell editor
        JTextField textField = new org.ofbiz.ordermax.base.ObservingTextField();
        DefaultCellEditor datePickerCellEditor = new DateTimeTableCellEditor(textField);

// Set the number of mouse clicks needed to activate it.
        datePickerCellEditor.setClickCountToStart(2);

// Set it for the appropriate table column.
//tableReceiveInv.getColumnModel().getColumn(DATE_COLUMN_INDEX).setCellEditor(datePickerCellEditor);        
        int colCount = receiveInventoryTableModel.getColumnCount();
        ReceiveInventoryTableModel.Columns[] columns = ReceiveInventoryTableModel.Columns.values();

        for (int i = 0; i < colCount; i++) {
            TableColumn column = tableReceiveInv.getColumnModel().getColumn(i);
            ReceiveInventoryTableModel.Columns columnValues = columns[i];
            column.setPreferredWidth(columnValues.getColumnWidth());

            if (i == ReceiveInventoryTableModel.Columns.INVENTORY_ITEM_TYPE.getColumnIndex()) {
                TableColumn tmpColum = column;
                setInventoryItemType(tableReceiveInv, tmpColum);
            } else if (i == ReceiveInventoryTableModel.Columns.REJECTION_REASON.getColumnIndex()) {
                TableColumn tmpColum = column;
                setUpRejectionReason(tableReceiveInv, tmpColum);
            }

            if (receiveInventoryTableModel.getColumnClass(i).equals(BigDecimal.class)) {
                column.setCellRenderer(new DecimalFormatRenderer());
            } else if (receiveInventoryTableModel.getColumnClass(i).equals(java.sql.Timestamp.class)) {
                column.setCellEditor(datePickerCellEditor);
            }
        }
    }

    public void setInventoryItemType(JTable table,
            TableColumn sportColumn) {
        //Set up the editor for the sport cells.
        JComboBox comboBox = new JComboBox();
        for (InventoryItemType inventoryItemType : InventoryItemTypeSingleton.getValueList()) {
            comboBox.addItem(inventoryItemType.getdescription());
        }

        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer
                = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        sportColumn.setCellRenderer(renderer);
    }

    public void setUpRejectionReason(JTable table,
            TableColumn sportColumn) {

        //Set up the editor for the sport cells.
        JComboBox comboBox = new JComboBox();
        for (RejectionReason reason : RejectionReasonSingleton.getValueList()) {
            comboBox.addItem(reason.getdescription());
        }

        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer
                = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        sportColumn.setCellRenderer(renderer);
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

        panelHeader = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblShipmentId = new javax.swing.JLabel();
        btnReceiveAll = new javax.swing.JButton();
        btnRejectAll = new javax.swing.JButton();
        btnClearAll = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        cBoxPrintStockTicket = new javax.swing.JCheckBox();
        panelDetails = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableReceiveInv = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        panelHeader.setPreferredSize(new java.awt.Dimension(10, 100));

        jLabel1.setText("Receive Purchase Order:");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Shipment Id:");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);

        btnReceiveAll.setText("Receive All");

        btnRejectAll.setText("Reject All");

        btnClearAll.setText("Clear All");

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 221, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        cBoxPrintStockTicket.setSelected(true);
        cBoxPrintStockTicket.setText("Print Stock Tickets");

        javax.swing.GroupLayout panelHeaderLayout = new javax.swing.GroupLayout(panelHeader);
        panelHeader.setLayout(panelHeaderLayout);
        panelHeaderLayout.setHorizontalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4)
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblShipmentId, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHeaderLayout.createSequentialGroup()
                        .addComponent(btnReceiveAll)
                        .addGap(2, 2, 2)
                        .addComponent(btnRejectAll)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClearAll))
                    .addComponent(cBoxPrintStockTicket))
                .addContainerGap())
        );

        panelHeaderLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel2});

        panelHeaderLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel2, lblShipmentId});

        panelHeaderLayout.setVerticalGroup(
            panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelHeaderLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnReceiveAll)
                        .addComponent(btnRejectAll)
                        .addComponent(btnClearAll)))
                .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelHeaderLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(panelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(lblShipmentId, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelHeaderLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cBoxPrintStockTicket)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        add(panelHeader, java.awt.BorderLayout.PAGE_START);

        panelDetails.setLayout(new java.awt.BorderLayout());

        tableReceiveInv.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tableReceiveInv);

        panelDetails.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(panelDetails, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnClearAll;
    public javax.swing.JButton btnReceiveAll;
    public javax.swing.JButton btnRejectAll;
    private javax.swing.JCheckBox cBoxPrintStockTicket;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lblShipmentId;
    private javax.swing.JPanel panelDetails;
    private javax.swing.JPanel panelHeader;
    private javax.swing.JTable tableReceiveInv;
    // End of variables declaration//GEN-END:variables
}
