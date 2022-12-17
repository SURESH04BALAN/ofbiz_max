/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns;

import java.awt.Color;
import java.awt.Component;
import java.awt.Toolkit;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
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
import javolution.util.FastMap;
import mvc.model.list.JComboBoxSelectionModel;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.ActionTableModel;
import mvc.model.table.ReturnItemViewTableModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.guiapp.xui.XuiContainer;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.rounding;
import static org.ofbiz.ordermax.celleditors.BigDecimalTableCellEditor.scale;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.PaymentComposite;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.entity.DisplayNameInterface;
import org.ofbiz.ordermax.entity.OrderHeader;
import org.ofbiz.ordermax.entity.PaymentType;
import org.ofbiz.ordermax.entity.ReturnItem;
import org.ofbiz.ordermax.entity.ReturnReason;
import org.ofbiz.ordermax.entity.ReturnType;
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.StatusSingleton;
import static org.ofbiz.ordermax.orderbase.returns.MaintainOrderReturnController.module;
import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public class OrderReturnItemsViewListPanel extends javax.swing.JPanel implements OrderReturnItemListInterface{

    private final JTextField txtProdIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;

//    private ReturnTableModel paymentTableModel = new ReturnTableModel();
    public JGenericComboBoxSelectionModel<OrderHeader> paymentStatusItemComboModel = null;
    //protected JScrollPane scroller;

    /**
     * Creates new form ReceiveInventoryPanel
     */
    public OrderReturnItemsViewListPanel() {
        initComponents();

        returnItemViewTableModel.addTableModelListener(new InteractiveTableModelListener());
        txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
        editor.setClickCountToStart(0);
        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);

        tableReturnItems.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableReturnItems.setSurrendersFocusOnKeystroke(true);
        tableReturnItems.setRowHeight(20);
        tableReturnItems.setSelectionBackground(Color.WHITE);
        tableReturnItems.setSelectionForeground(Color.BLACK);
        tableReturnItems.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableReturnItems.getTableHeader().setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tableReturnItems.setModel(returnItemViewTableModel);
        tableReturnItems.setFillsViewportHeight(true);
        tableReturnItems.setPreferredScrollableViewportSize(Toolkit.getDefaultToolkit().getScreenSize());

        setupEditOrderTable();
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelLookupInvoice, "Order Details");
        ComponentBorder.doubleRaisedLoweredBevelBorder(panelInvoiceList, "Return Item List");
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
        return lblReturnId.getText();
    }

    JGenericComboBoxSelectionModel<ReturnReason> returnReason = null;

    final public void setupEditOrderTable() {

        returnReason = new JGenericComboBoxSelectionModel<ReturnReason>(ReturnReasonSingleton.getValueList());

//        tableReceiveInv.setSelectAllForEdit(true);
      /*  tableReturnItems.setSurrendersFocusOnKeystroke(true);
         txtProdIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
         DefaultCellEditor editor = new DefaultCellEditor(txtProdIdTableTextField);
         editor.setClickCountToStart(0);
         productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);
         tableReturnItems.getColumn("Return ID").setCellEditor(productTreeActionTableCellEditor);

         for (int i = 0; i < ReturnTableModel.Columns.values().length; i++) {

         ReturnTableModel.Columns[] columns = ReturnTableModel.Columns.values();
         ReturnTableModel.Columns column = columns[i];
         TableColumn col = tableReturnItems.getColumnModel().getColumn(i);
         col.setPreferredWidth(column.getColumnWidth());
         if (column.getClassName().equals(BigDecimal.class)) {
         col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
         } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
         Debug.logError("Date format", "module");
         col.setCellRenderer(new DateFormatCellRenderer());
         }
         }

         */
        int colCount = returnItemViewTableModel.getColumnCount();
        ReturnItemViewTableModel.Columns[] columns = ReturnItemViewTableModel.Columns.values();

        for (int i = 0; i < colCount; i++) {
            TableColumn column = tableReturnItems.getColumnModel().getColumn(i);
            ReturnItemViewTableModel.Columns columnValues = columns[i];
            column.setPreferredWidth(columnValues.getColumnWidth());
            ReturnItemViewTableModel.Columns columnVal = columns[i];
            if (i == ReturnItemViewTableModel.Columns.ORDER_PROD_ID_INDEX.getColumnIndex()) {
                column.setCellEditor(productTreeActionTableCellEditor);
            } else if (i == ReturnItemViewTableModel.Columns.REASON_INDEX.getColumnIndex()) {

                TableColumn tmpColum = column;
                setUpSportColumn(tableReturnItems, tmpColum);
            } else if (i == ReturnItemViewTableModel.Columns.ITEM_STATUS_INDEX.getColumnIndex()) {
                TableColumn tmpColum = column;
                setUpStatusColumn(tableReturnItems, tmpColum);
            } else if (i == ReturnItemViewTableModel.Columns.RETURN_TYPE.getColumnIndex()) {
                TableColumn tmpColum = column;
                setupReturnTypeColumn(tableReturnItems, tmpColum);
            }

            if (columnVal.getClassName().equals(BigDecimal.class)) {
                column.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            }
        }

        initColumnSizes(tableReturnItems);
    }
    
    public JButton getFindBtn(){
        return null;
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
    ReturnItemViewTableModel returnItemViewTableModel = new ReturnItemViewTableModel();

    @Override
    public ActionTableModel getOrderEntryTableModel() {
        return returnItemViewTableModel;
    }

    @Override
    public JPanel getPanel() {
        return this;
    }


    ReturnHeaderComposite returnHeaderComposite = null;
    
    public void setReturnHeaderComposite(ReturnHeaderComposite val){
        returnHeaderComposite = val;
    }
    
    public ReturnHeaderComposite getReturnHeaderComposite(){
        return returnHeaderComposite;
    }  

    @Override
    public void setOrderComboModelList(List<OrderHeader> list) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getInvoiceId() {
        return "";
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

    public void clearDialogFields() {

    }

    public void setOrder(Order order) {

    }
    private ListModel<ReturnItemComposite> orderItemCompositeListModel = null;

    public void setDialogFields() {
        
        BigDecimal val = BigDecimal.ZERO;
        for (int i = 0; i < orderItemCompositeListModel.getSize(); i++) {

            ReturnItem ret = orderItemCompositeListModel.getElementAt(i).getReturnItem();
            Debug.logError(ret.getreturnPrice().toString(), module);
            Debug.logError(ret.getreturnQuantity().toString(), module);
            if (UtilValidate.isNotEmpty(ret.getreturnPrice())
                    && UtilValidate.isNotEmpty(ret.getreturnQuantity())) {

                val = val.add(ret.getreturnPrice().multiply(ret.getreturnQuantity()));
            }
        }
        lblReturnTotal.setText(val.toString());
        lblReturnId.setText(getReturnHeaderComposite().getReturnHeader().getreturnId());
    }


    public void reloadItemDataModel(ListModel<ReturnItemComposite> cutdownList) {
        orderItemCompositeListModel = cutdownList;
        returnItemViewTableModel.setListModel(cutdownList);
        tableReturnItems.setModel(returnItemViewTableModel);
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
        jLabel9 = new javax.swing.JLabel();
        lblReturnId = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblReturnTotal = new javax.swing.JLabel();
        lblOrderId = new javax.swing.JLabel();
        lblOrderIdName = new javax.swing.JLabel();
        panelInvoiceList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableReturnItems = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(911, 400));
        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        panelLookupInvoice.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Return Items", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Return Id:");

        jLabel2.setText("Retrun Total:");

        lblOrderIdName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblOrderIdName.setText("Order Id:");

        javax.swing.GroupLayout panelLookupInvoiceLayout = new javax.swing.GroupLayout(panelLookupInvoice);
        panelLookupInvoice.setLayout(panelLookupInvoiceLayout);
        panelLookupInvoiceLayout.setHorizontalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblOrderIdName, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblReturnId, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOrderId, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblReturnTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelLookupInvoiceLayout.setVerticalGroup(
            panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(panelLookupInvoiceLayout.createSequentialGroup()
                        .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblReturnTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblOrderId, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelLookupInvoiceLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                                .addComponent(lblOrderIdName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblReturnId, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel2.add(panelLookupInvoice, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        panelInvoiceList.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Items List", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N
        panelInvoiceList.setLayout(new java.awt.GridLayout(1, 0));

        tableReturnItems.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tableReturnItems);

        panelInvoiceList.add(jScrollPane1);

        add(panelInvoiceList, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    public class InteractiveTableModelListener implements TableModelListener {

        public void tableChanged(TableModelEvent evt) {
            if (evt.getType() == TableModelEvent.UPDATE) {
                int column = evt.getColumn();
                int row = evt.getFirstRow();
                System.out.println("row: " + row + " column: " + column);
                if (column + 1 < returnItemViewTableModel.getColumnCount()) {
                    tableReturnItems.setColumnSelectionInterval(column + 1, column + 1);
                }
                if (row < returnItemViewTableModel.getRowCount()) {
                    tableReturnItems.setRowSelectionInterval(row, row);
                }

//                setTotals();
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JLabel lblOrderId;
    private javax.swing.JLabel lblOrderIdName;
    public javax.swing.JLabel lblReturnId;
    public javax.swing.JLabel lblReturnTotal;
    private javax.swing.JPanel panelInvoiceList;
    private javax.swing.JPanel panelLookupInvoice;
    private javax.swing.JTable tableReturnItems;
    // End of variables declaration//GEN-END:variables
}
