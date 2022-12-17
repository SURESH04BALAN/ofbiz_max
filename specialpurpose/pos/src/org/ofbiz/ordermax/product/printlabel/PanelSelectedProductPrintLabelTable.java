/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.printlabel;

import java.math.BigDecimal;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import mvc.controller.RowColumnClickActionTableCellEditor;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListModelSelection;
import mvc.model.table.LabelPrintListTableModel;
import mvc.model.table.ProductCompositePrintLabelTableModel;

import org.ofbiz.base.util.Debug;
import org.ofbiz.ordermax.base.RXTable;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.ProductCompositeLabelPrint;

import org.ofbiz.ordermax.utility.ComponentBorder;

/**
 *
 * @author siranjeev
 */
public final class PanelSelectedProductPrintLabelTable extends javax.swing.JPanel {

    public final JTextField txtCategoryIdTableTextField = new JTextField();
    private RowColumnClickActionTableCellEditor rowColumnClickActionTableCellEditor = null;
    public RXTable tableReceiveInv;
    protected JScrollPane scrollerPriceCalculation;

    public RXTable getTable() {
        return tableReceiveInv;
    }

    public LabelPrintListTableModel productCompositePrintLabelTableModel = new LabelPrintListTableModel();
    private ListAdapterListModel<ProductCompositeLabelPrint> itemListModel = new ListAdapterListModel<ProductCompositeLabelPrint>();
    private ListModelSelection<ProductCompositeLabelPrint> listModelSelection = new ListModelSelection<ProductCompositeLabelPrint>();
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();
    boolean showComboKeys = false;

    /**
     * Creates new form ReceiveInventoryPanel
     */
    public PanelSelectedProductPrintLabelTable() {
        initComponents();
        
        tableReceiveInv = new RXTable() {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        
       
        tableReceiveInv.setModel(productCompositePrintLabelTableModel);
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableReceiveInv.setSelectionModel(selectionModel);

        setupEditOrderTable();
        setMemberList(itemListModel);
        ComponentBorder.doubleRaisedLoweredBevelBorder(this, "Product Print List");
    }

    public void setMemberList(ListAdapterListModel<ProductCompositeLabelPrint> listModel) {
        //    accountListModel.setModel(listModel);
        productCompositePrintLabelTableModel.setListModel(listModel);
        listModelSelection.setListModels(listModel, selectionModel);
//        accountCompositeTableModel.setListModel(listModel);
    }

    public RowColumnClickActionTableCellEditor getCategoryActionTableCellEditor() {
        return rowColumnClickActionTableCellEditor;
    }

    final public void setupEditOrderTable() {
        tableReceiveInv.setSelectAllForEdit(true);
        tableReceiveInv.setSurrendersFocusOnKeystroke(true);

        for (int i = 0; i < LabelPrintListTableModel.Columns.values().length; i++) {
            LabelPrintListTableModel.Columns[] columns = LabelPrintListTableModel.Columns.values();
            LabelPrintListTableModel.Columns column = columns[i];
            TableColumn col = tableReceiveInv.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());

            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());
            } else if (LabelPrintListTableModel.Columns.PRODUCTID == column) {
                tableReceiveInv.setSurrendersFocusOnKeystroke(true);
                txtCategoryIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
                DefaultCellEditor editor = new DefaultCellEditor(txtCategoryIdTableTextField);
                editor.setClickCountToStart(0);
                rowColumnClickActionTableCellEditor = new RowColumnClickActionTableCellEditor(editor);
                col.setCellEditor(rowColumnClickActionTableCellEditor);
            }
        }

        scrollerPriceCalculation = new javax.swing.JScrollPane(tableReceiveInv);
        jPanel2.setLayout(new java.awt.GridLayout(0, 1));
        jPanel2.add(scrollerPriceCalculation);
    }

//    private List itemList = new ArrayList<ProductCompositeLabelPrint>();
    public List getProductCompositeLabelPrintList() {
        return itemListModel.getList();
    }

    public void setProductCompositeLabelPrintList(ListAdapterListModel list) {
        itemListModel = list;
    }

    public void setDialogField() {

        setMemberList(itemListModel);

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

        setMinimumSize(new java.awt.Dimension(0, 200));
        setPreferredSize(new java.awt.Dimension(911, 500));
        setLayout(new java.awt.BorderLayout());
        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
