/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.report;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.math.BigDecimal;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javolution.util.FastMap;
import mvc.controller.LoadPaymentWorker;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.report.PaymentAgeingReportTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.composite.PaymentComposite;

/**
 *
 * @author BBS Auctions
 */
public class ReportMainPanel extends javax.swing.JPanel {

    public GenericTableModelPanel<PaymentComposite, PaymentAgeingReportTableModel> tablePanel = null;
    final private ListAdapterListModel<PaymentComposite> invoiceCompositeListModel = new ListAdapterListModel<PaymentComposite>();
    private static int HEADER_HEIGHT = 32;

    /**
     * Creates new form ReportMainPanel
     */
    public ReportMainPanel() {
        initComponents();

        tablePanel = new GenericTableModelPanel<PaymentComposite, PaymentAgeingReportTableModel>(new PaymentAgeingReportTableModel());
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.CENTER, tablePanel);
        tablePanel.jTable.setShowHorizontalLines(false);
        tablePanel.jTable.setShowVerticalLines(false);
        tablePanel.jTable.setFocusable(false);
        JTableHeader header = tablePanel.jTable.getTableHeader();

//        header.setPreferredSize(new Dimension(100, HEADER_HEIGHT));
//        tablePanel.jTable.setTableHeader(null);
        tablePanel.jTable.getTableHeader().setBackground(java.awt.Color.WHITE);
        tablePanel.jTable.getParent().setBackground(java.awt.Color.WHITE);
        tablePanel.jTable.setBackground(java.awt.Color.WHITE);
        tablePanel.scrollPane.setColumnHeader(new JViewport() {
            @Override
            public Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                d.height = HEADER_HEIGHT;
                return d;
            }
        });
//        tablePanel.jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        sizeColumns(tablePanel.jTable);
        Map<String, Object> findOptionList = getFindOptionList();
        tablePanel.setListModel(invoiceCompositeListModel);
        for (int i = 0; i < PaymentAgeingReportTableModel.Columns.values().length; i++) {
            PaymentAgeingReportTableModel.Columns[] columns = PaymentAgeingReportTableModel.Columns.values();
            PaymentAgeingReportTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());
            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer());
            }
            col.setHeaderRenderer(new MyTableHeaderRenderer());
        }

        //LoadPurchaseOrderWorker worker = new LoadPurchaseOrderWorker(invoiceCompositeListModel, session, findOptionList);
        LoadPaymentWorker worker = new LoadPaymentWorker(invoiceCompositeListModel, XuiContainer.getSession(), findOptionList);
        tablePanel.actionPerformed(worker);

    }

    private void sizeColumns(JTable table) {
        TableColumnModel columns = table.getColumnModel();
        TableModel data = table.getModel();
        int margin = columns.getColumnMargin() * 2;
        int columnCount = columns.getColumnCount();
        int rowCount = data.getRowCount();
        for (int col = 0; col < columnCount; col++) {
            TableColumn column = columns.getColumn(col);
            int modelCol = column.getModelIndex();
            int width = 0;
            for (int row = 0; row < rowCount; row++) {
                TableCellRenderer r = table.getCellRenderer(row, col);
                int w = r.getTableCellRendererComponent(
                        table, data.getValueAt(row, modelCol), false, false, row, col)
                        .getPreferredSize().width;
                if (w > width) {
                    width = w;
                }
            }
            column.setPreferredWidth(width + margin);
        }
    }

    public Map<String, Object> getFindOptionList() {
        Map<String, Object> findOptionList = FastMap.newInstance();

        boolean showAll = true;
        if (showAll == true) {
            findOptionList.put("showAll", "Y");
            findOptionList.put("noConditionFind", "Y");
        }

        findOptionList.put("lookupFlag", "Y");
        /*        
         if(txtComments.getText()!=null && txtComments.getText().isEmpty()==false){
         findOptionList.put("description", txtDescription.getText());            
         }        

       
         */
        //status type method

        return findOptionList;
    }
    private static final Font labelFont = new Font("Arial", Font.BOLD, 14);

    class MyTableHeaderRenderer extends JLabel implements TableCellRenderer {

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int rowIndex, int vColIndex) {
            setText(value.toString());
            setToolTipText((String) value);
            setFont(labelFont);
//            setHorizontalAlignment(SwingConstants.CENTER);
            setBorder(BorderFactory.createEmptyBorder());
            setBackground(java.awt.Color.WHITE);
            return this;
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
