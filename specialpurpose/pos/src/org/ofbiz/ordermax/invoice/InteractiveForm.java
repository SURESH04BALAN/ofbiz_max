/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.invoice;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import mvc.controller.LoadPurchaseOrderWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.orders.OrderFindHelper;
import org.ofbiz.ordermax.party.PartyListSingleton;
import org.ofbiz.ordermax.screens.action.PurchaseOrderAction;

/**
 *
 * @author siranjeev
 */
public class InteractiveForm extends JPanel {

    public static final String[] columnNames = {
        "Order Id", "", "Party Name", "Order Status", "Invoice status", "Invoice Ids","Order Amount","Invoice Amount","Load Order"
    };

    protected JTable table;
    protected JScrollPane scroller;
    protected InteractiveTableModel tableModel;

    public InteractiveTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(InteractiveTableModel tableModel) {
        this.tableModel = tableModel;
    }
    protected XuiSession session = null;
    protected javax.swing.JDesktopPane desktopPane = null;
    ListAdapterListModel<Order> orderListModel = null;
    
    public InteractiveForm(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        this.session = session;
        this.desktopPane = desktopPane;
        initComponent();
    }

    public InteractiveForm(ListAdapterListModel<Order> orderListModel, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        this.orderListModel = orderListModel;
        this.session = session;
        this.desktopPane = desktopPane; 
        initComponent();
 

    }

    public void initComponent() {
        tableModel = new InteractiveTableModel(columnNames);
        tableModel.addTableModelListener(new InteractiveForm.InteractiveTableModelListener());
        table = new JTable();
        table.setModel(tableModel);
        table.setSurrendersFocusOnKeystroke(true);
        Debug.logInfo("orderListModel.getsizr: " + orderListModel.getSize(), TOOL_TIP_TEXT_KEY);
        if(orderListModel!=null){
            
            for(Order ord : orderListModel.getList()){
                
                String orderId = ord.getOrderId();
                OrderInvoiceGenerateRecord audioRecord = tableModel.addEmptyRow();
            Order order = LoadPurchaseOrderWorker.loadOrder(orderId, session);
    //                            orderListModel.add(order);
            Debug.logInfo("orderId: " + orderId, "String");
            //get invoices
            audioRecord.getInvoicesListModel().clear();
            audioRecord.getInvoicesListModel().addAll(LoadInvoiceWorker.loadPurchaseOrderComposites(order.getInvoiceIds(), session));

            //set details
            audioRecord.setOrderId(orderId);
            audioRecord.setOrder(order);
 
                               try {
                                    audioRecord.setPartyName(PartyListSingleton.getAccount(order.getPartyId()).getDisplayName());
                                } catch (Exception ex) {
                                }            
            audioRecord.setOrderStatus(order.getOrderStatusId());

            //set button
            PurchaseOrderAction purchaserAction = new PurchaseOrderAction(orderId, "Purchaser Order", session, desktopPane);
            JButton btn = purchaserAction.createActionButton();
            audioRecord.setOrderButton(btn);//.setAction(purchaserAction);

            StringBuilder invoicToStringBuilder = new StringBuilder();
            StringBuilder invoicStatusStringBuilder = new StringBuilder();

            for (InvoiceComposite ic : audioRecord.getInvoicesListModel().getList()) {
                if (invoicToStringBuilder.length() > 0) {
                    invoicToStringBuilder.append(",");
                }

                invoicToStringBuilder.append(ic.getInvoice().getinvoiceId());

                if (invoicStatusStringBuilder.length() > 0) {
                    invoicStatusStringBuilder.append(";");
                }

                invoicStatusStringBuilder.append(ic.getInvoice().getinvoiceId());
                invoicStatusStringBuilder.append("=");
                invoicStatusStringBuilder.append(ic.getInvoice().getstatusId());
            }
            audioRecord.setInvoiceIds(invoicToStringBuilder.toString());
            audioRecord.setInvoiceStatus(invoicStatusStringBuilder.toString());
            audioRecord.setOrderAmount(order.getGrandTotal());
            
        }
        }
        if (!tableModel.hasEmptyRow()) {
            tableModel.addEmptyRow();
            table.setRowHeight(0, 38);            
        }

        
        scroller = new javax.swing.JScrollPane(table);
        table.setPreferredScrollableViewportSize(new java.awt.Dimension(500, 300));

        TableCellRenderer buttonRenderer = new JTableButtonRenderer();
        table.getColumn("Load Order").setCellRenderer(buttonRenderer);
//        table.getColumn("Button2").setCellRenderer(buttonRenderer);
        table.addMouseListener(new JTableButtonMouseListener(table));
        
//          table.setRowHeight(1, 15);
        
        setLayout(new BorderLayout());
        add(scroller, BorderLayout.CENTER);
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public void highlightLastRow(int row) {
        int lastrow = tableModel.getRowCount();
        if (row == lastrow - 1) {
            table.setRowSelectionInterval(lastrow - 1, lastrow - 1);
        } else {
            table.setRowSelectionInterval(row + 1, row + 1);
        }

        table.setColumnSelectionInterval(0, 0);
    }

    public class InteractiveTableModelListener implements TableModelListener {

        public void tableChanged(TableModelEvent evt) {
            if (evt.getType() == TableModelEvent.UPDATE) {
                int column = evt.getColumn();
                int row = evt.getFirstRow();
                System.out.println("row: " + row + " column: " + column);
                table.setColumnSelectionInterval(column + 1, column + 1);
                table.setRowSelectionInterval(row, row);
            }
        }
    }

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
    
    public List<OrderInvoiceGenerateRecord> getDataList() {        
        return tableModel.getDataList();
    }
}
