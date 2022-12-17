/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.table.ReturnItemTableModel;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.components.InvoicePickerEditPanel;
import org.ofbiz.ordermax.base.components.OrderPickerEditPanel;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.rounding;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.scale;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.OrderHeader;
import org.ofbiz.ordermax.entity.ReturnReason;
import org.ofbiz.ordermax.entity.ReturnType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class OrderReturnItemsListPanel extends javax.swing.JPanel implements OrderReturnItemListInterface {

    private final JTextField txtProdIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;
    public OrderPickerEditPanel panelOrderIdPicker;
     public InvoicePickerEditPanel panelInvoiceIdPicker;
    ControllerOptions options = null;
    // private JGenericComboBoxSelectionModel<OrderHeader> orderComboModel = null;
    //protected JScrollPane scroller;
/*
     public JGenericComboBoxSelectionModel<OrderHeader> getOrderComboModel() {
     return orderComboModel;
     }
     */

    public void setOrderComboModelList(List<OrderHeader> list) {
        //orderComboModel = new JGenericComboBoxSelectionModel<OrderHeader>(panelOrderHeaderId, list, DisplayNameInterface.DisplayTypes.SHOW_CODE_ONLY);
    }

    /**
     * Creates new form ReceiveInventoryPanel
     */
    public OrderReturnItemsListPanel(ControllerOptions options) {
        initComponents();
        this.options = options;
//        List<OrderHeader> findStatusPaymentList = new ArrayList<OrderHeader>();//StatusSingleton.getValueList("PMNT_STATUS");     
        //orderComboModel = new JGenericComboBoxSelectionModel<OrderHeader>(panelOrderHeaderId, DisplayNameInterface.DisplayTypes.SHOW_CODE_ONLY);

        //panelOrderHeaderId.setLayout(new BorderLayout());
        //panelOrderHeaderId.add(BorderLayout.CENTER, orderComboModel.jComboBox);
//        orderEntryTableModel = new ReturnItemTableModel();
//        ControllerOptions orderOptions = new ControllerOptions(options);
        panelOrderIdPicker = new OrderPickerEditPanel(options.getCopy());
        panelOrderHeaderId.setLayout(new BorderLayout());
        panelOrderHeaderId.add(BorderLayout.CENTER, panelOrderIdPicker);

        panelInvoiceIdPicker = new InvoicePickerEditPanel(options.getCopy());
        panelInvoiceId.setLayout(new BorderLayout());
        panelInvoiceId.add(BorderLayout.CENTER, panelInvoiceIdPicker);
        
        orderEntryTableModel.addTableModelListener(new InteractiveTableModelListener());
        txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
        editor.setClickCountToStart(0);
        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);

//        tableReturnItems = new RXTable();// {
//            public boolean getScrollableTracksViewportWidth() {
        //               return getPreferredSize().width < getParent().getWidth();
        //           }
        //       }; 
        tableReturnItems.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//        tableReturnItems.setSelectAllForEdit(true);
        tableReturnItems.setSurrendersFocusOnKeystroke(true);
        tableReturnItems.setRowHeight(20);
        tableReturnItems.setSelectionBackground(Color.WHITE);
        tableReturnItems.setSelectionForeground(Color.BLACK);
        tableReturnItems.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableReturnItems.getTableHeader().setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableReturnItems.setModel(orderEntryTableModel);
        tableReturnItems.setFillsViewportHeight(true);
        tableReturnItems.setPreferredScrollableViewportSize(Toolkit.getDefaultToolkit().getScreenSize());

        setupEditOrderTable();
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelLookupInvoice, "Order Details");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelInvoiceList, "Return Item List");

//        btnToPartyId.addActionListener(new LookupActionListner(txtToPartyId, "partyIdTextField", "BILL_FROM_VENDOR", null));        
    }
    boolean isReportView = true;

    public boolean isIsReportView() {
        return isReportView;
    }

    public void setIsReportView(boolean isReportView) {
        this.isReportView = isReportView;
    }

    public JTextField getTxtProdIdTableTextField() {
        return txtProdIdTableTextField;
    }

    public ProductTreeActionTableCellEditor getProductTreeActionTableCellEditor() {
        return productTreeActionTableCellEditor;
    }

    @Override
    public String getOrderId() {
        return panelOrderIdPicker.textIdField.getText();
    }
    /*  @Override
     public Map<String, Object> getFindOptionList() {

     boolean findVal = false;

     Map<String, Object> findOptionList = FastMap.newInstance();

     if (UtilValidate.isNotEmpty(panelOrderIdPicker.textIdField.getText())) {
     findOptionList.put("orderId", panelOrderIdPicker.textIdField.getText());
     findVal = true;
     }

     //status type method
     return findOptionList;
     }
     */

    public JPanel getPanel() {
        return this;
    }

    JGenericComboBoxSelectionModel<ReturnReason> returnReason = null;

    @Override
    final public void setupEditOrderTable() {

        int colCount = orderEntryTableModel.getColumnCount();
        ReturnItemTableModel.Columns[] columns = ReturnItemTableModel.Columns.values();

        for (int i = 0; i < colCount; i++) {
            TableColumn column = tableReturnItems.getColumnModel().getColumn(i);
            ReturnItemTableModel.Columns columnValues = columns[i];
            column.setPreferredWidth(columnValues.getColumnWidth());
            ReturnItemTableModel.Columns columnVal = columns[i];
            if (i == ReturnItemTableModel.Columns.ORDER_PROD_ID_INDEX.getColumnIndex()) {
                column.setCellEditor(productTreeActionTableCellEditor);
            } else if (i == ReturnItemTableModel.Columns.REASON_INDEX.getColumnIndex()) {

                TableColumn tmpColum = column;
                setUpSportColumn(tableReturnItems, tmpColum);
            } else if (i == ReturnItemTableModel.Columns.ITEM_STATUS_INDEX.getColumnIndex()) {
                TableColumn tmpColum = column;
                setUpStatusColumn(tableReturnItems, tmpColum);
            } else if (i == ReturnItemTableModel.Columns.RETURN_TYPE.getColumnIndex()) {
                TableColumn tmpColum = column;
                setupReturnTypeColumn(tableReturnItems, tmpColum);
            }

            if (columnVal.getClassName().equals(BigDecimal.class)) {
                column.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            }
        }

        initColumnSizes(tableReturnItems);
    }

    public void setUpSportColumn(JTable table,
            TableColumn sportColumn) {
        //Set up the editor for the sport cells.
        JComboBox comboBox = new JComboBox();
        for (ReturnReason reason : ReturnReasonSingleton.getValueList()) {
            comboBox.addItem(reason.getdescription());
        }

        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer
                = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        sportColumn.setCellRenderer(renderer);
    }

    public void setUpStatusColumn(JTable table,
            TableColumn sportColumn) {

        //Set up the editor for the sport cells.
        JComboBox comboBox = new JComboBox();
        for (StatusItem reason : StatusSingleton.getValueList("INV_SERIALIZED_STTS")) {
            comboBox.addItem(reason.getdescription());
        }

        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer
                = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        sportColumn.setCellRenderer(renderer);
    }

    public void setupReturnTypeColumn(JTable table,
            TableColumn sportColumn) {

        //Set up the editor for the sport cells.
        JComboBox comboBox = new JComboBox();
        for (ReturnType reason : ReturnTypeSingleton.getValueList()) {
            comboBox.addItem(reason.getdescription());
        }

        sportColumn.setCellEditor(new DefaultCellEditor(comboBox));

        //Set up tool tips for the sport cells.
        DefaultTableCellRenderer renderer
                = new DefaultTableCellRenderer();
        renderer.setToolTipText("Click for combo box");
        sportColumn.setCellRenderer(renderer);

    }

    private void initColumnSizes(JTable table) {
        /*    ReturnItemTableModel model = (ReturnItemTableModel)table.getModel();
         TableColumn column = null;
         Component comp = null;
         int headerWidth = 0;
         int cellWidth = 0;
         Object[] longValues = model.longValues;
         TableCellRenderer headerRenderer =
         table.getTableHeader().getDefaultRenderer();

         for (int i = 0; i < 5; i++) {
         column = table.getColumnModel().getColumn(i);

         comp = headerRenderer.getTableCellRendererComponent(
         null, column.getHeaderValue(),
         false, false, 0, 0);
         headerWidth = comp.getPreferredSize().width;

         comp = table.getDefaultRenderer(model.getColumnClass(i)).
         getTableCellRendererComponent(
         table, longValues[i],
         false, false, 0, i);
         cellWidth = comp.getPreferredSize().width;

    
         column.setPreferredWidth(Math.max(headerWidth, cellWidth));
         }*/
    }

    @Override
    public String getInvoiceId() {
        return panelInvoiceIdPicker.textIdField.getText();
    }

    class CheckBoxCellRenderer<E> implements TableCellRenderer {

        JComboBox<E> combo;

        public CheckBoxCellRenderer(JComboBox<E> comboBox) {
            this.combo = new JComboBox<E>();
            this.combo.setRenderer(new GenericListCellRenderer<E>());
            for (int i = 0; i < comboBox.getItemCount(); i++) {
                combo.addItem(comboBox.getItemAt(i));
            }
        }

        public Component getTableCellRendererComponent(JTable jtable, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            combo.setSelectedItem(value);
            return combo;
        }
    }

    private class GenericListCellRenderer<E> extends JLabel implements
            ListCellRenderer<E> {

        /**
         *
         */
        private static final long serialVersionUID = -1614367901813214864L;
        DisplayNameInterface.DisplayTypes showKey;

        public GenericListCellRenderer(DisplayNameInterface.DisplayTypes showKey) {
            super();
            setOpaque(true);
            this.showKey = showKey;
        }

        public GenericListCellRenderer() {
            super();
            setOpaque(true);
            this.showKey = XuiContainer.getSession().getComboBoxDisplayFormat();
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends E> list,
                E value, int index, boolean isSelected, boolean cellHasFocus) {
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
//        if (-1 < index) {
//          list.setToolTipText(tooltips[index]);
//        }
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setFont(list.getFont());
            if (value instanceof DisplayNameInterface) {
                String toString = toString((DisplayNameInterface) value);
                setText(toString);
            }

            return this;
        }

        private String toString(DisplayNameInterface obj) {
            if (obj == null) {
                return "";
            } else {
                return obj.getdisplayName(showKey);
            }
        }

    }

    @Override
    public void clearDialogFields() {
        panelOrderIdPicker.textIdField.setText("");
    }

    public void setOrder(Order order) {

    }
    private ListModel<ReturnItemComposite> orderItemCompositeListModel = null;

    @Override
    public void setDialogFields() {
        /* BigDecimal val = BigDecimal.ZERO;
         for (int i = 0; i < orderItemCompositeListModel.getSize(); i++) {

         ReturnItem ret = orderItemCompositeListModel.getElementAt(i).getReturnItem();
         Debug.logError(ret.getreturnPrice().toString(), module);
         Debug.logError(ret.getreturnQuantity().toString(), module);
         if (UtilValidate.isNotEmpty(ret.getreturnPrice())
         && UtilValidate.isNotEmpty(ret.getreturnQuantity())) {

         val = val.add(ret.getreturnPrice().multiply(ret.getreturnQuantity()));
         }
         }*/
//        lblReturnTotal.setText(val.toString());
//        lblReturnId.setText(getReturnHeaderComposite().getReturnHeader().getreturnId());
    }

    @Override
    public JButton getFindBtn() {
        return btnFind;
    }

    private ReturnItemTableModel orderEntryTableModel = new ReturnItemTableModel();

    @Override
    public ReturnItemTableModel getOrderEntryTableModel() {
        return orderEntryTableModel;
    }

    public void reloadItemDataModel(ListModel<ReturnItemComposite> cutdownList) {
//        java.awt.Dimension dim = tableReturnItems.getPreferredSize();
        orderItemCompositeListModel = cutdownList;
        orderEntryTableModel.setListModel(cutdownList);
        tableReturnItems.setModel(orderEntryTableModel);
        tableReturnItems.repaint();
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

    public void setReturnTotal(BigDecimal decimal) {

    }

    public void setReturnId(String id) {

    }
    String partyId = null;

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String str) {
        partyId = str;
    }
    ReturnHeaderComposite returnHeaderComposite = null;

    public void setReturnHeaderComposite(ReturnHeaderComposite val) {
        returnHeaderComposite = val;
    }

    public ReturnHeaderComposite getReturnHeaderComposite() {
        return returnHeaderComposite;
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
        panelLookupInvoice = new javax.swing.JPanel();
        btnFind = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        panelOrderHeaderId = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        panelInvoiceId = new javax.swing.JPanel();
        panelInvoiceList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableReturnItems = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelLookupInvoice.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Payment Search Option", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        panelLookupInvoice.setPreferredSize(new java.awt.Dimension(911, 90));

        btnFind.setText("Load items for return");
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFindActionPerformed(evt);
            }
        });

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Order Id");

        javax.swing.GroupLayout panelOrderHeaderIdLayout = new javax.swing.GroupLayout(panelOrderHeaderId);
        panelOrderHeaderId.setLayout(panelOrderHeaderIdLayout);
        panelOrderHeaderIdLayout.setHorizontalGroup(
            panelOrderHeaderIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 188, Short.MAX_VALUE)
        );
        panelOrderHeaderIdLayout.setVerticalGroup(
            panelOrderHeaderIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Invoice Id");

        javax.swing.GroupLayout panelInvoiceIdLayout = new javax.swing.GroupLayout(panelInvoiceId);
        panelInvoiceId.setLayout(panelInvoiceIdLayout);
        panelInvoiceIdLayout.setHorizontalGroup(
            panelInvoiceIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 188, Short.MAX_VALUE)
        );
        panelInvoiceIdLayout.setVerticalGroup(
            panelInvoiceIdLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 25, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelLookupInvoiceLayout = new javax.swing.GroupLayout(panelLookupInvoice);
        panelLookupInvoice.setLayout(panelLookupInvoiceLayout);
        panelLookupInvoiceLayout.setHorizontalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGap(89, 89, 89)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelOrderHeaderId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelInvoiceId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFind, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(358, Short.MAX_VALUE))
        );

        panelLookupInvoiceLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel9});

        panelLookupInvoiceLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {panelInvoiceId, panelOrderHeaderId});

        panelLookupInvoiceLayout.setVerticalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(panelOrderHeaderId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(panelInvoiceId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel2.add(panelLookupInvoice, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        panelInvoiceList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Invoice List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        panelInvoiceList.setLayout(new java.awt.GridLayout(1, 0));

        tableReturnItems.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tableReturnItems);

        panelInvoiceList.add(jScrollPane1);

        add(panelInvoiceList, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFindActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnFindActionPerformed

    public class InteractiveTableModelListener implements TableModelListener {

        public void tableChanged(TableModelEvent evt) {
            if (evt.getType() == TableModelEvent.UPDATE) {
                int column = evt.getColumn();
                int row = evt.getFirstRow();
                System.out.println("row: " + row + " column: " + column);
                if (column + 1 < orderEntryTableModel.getColumnCount()) {
                    tableReturnItems.setColumnSelectionInterval(column + 1, column + 1);
                }
                if (row < orderEntryTableModel.getRowCount()) {
                    tableReturnItems.setRowSelectionInterval(row, row);
                }

//                setTotals();
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnFind;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelInvoiceId;
    private javax.swing.JPanel panelInvoiceList;
    private javax.swing.JPanel panelLookupInvoice;
    private javax.swing.JPanel panelOrderHeaderId;
    private javax.swing.JTable tableReturnItems;
    // End of variables declaration//GEN-END:variables
}
