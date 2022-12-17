/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.printlabel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;
import mvc.controller.RowColumnClickActionTableCellEditor;
import mvc.model.list.JGenericComboBoxSelectionModel;
import mvc.model.list.ListAdapterListModel;
import mvc.model.list.ListModelSelection;
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
public final class ProductPrintLabelTable extends javax.swing.JPanel {

    public final JTextField txtCategoryIdTableTextField = new JTextField();
    private RowColumnClickActionTableCellEditor rowColumnClickActionTableCellEditor = null;
    public RXTable tableReceiveInv;
    protected JScrollPane scrollerPriceCalculation;

    public RXTable getTable() {
        return tableReceiveInv;
    }

    public ProductCompositePrintLabelTableModel productCompositePrintLabelTableModel = new ProductCompositePrintLabelTableModel();
    private ListAdapterListModel<ProductCompositeLabelPrint> itemListModel = new ListAdapterListModel<ProductCompositeLabelPrint>();
    private ListModelSelection<ProductCompositeLabelPrint> listModelSelection = new ListModelSelection<ProductCompositeLabelPrint>();
    private ListSelectionModel selectionModel = new DefaultListSelectionModel();
    private JGenericComboBoxSelectionModel<String> productCategory = null;
    //private ListAdapterListModel<ProductComposite> invoiceCompositeListModel = new ListAdapterListModel<ProductComposite>();
//    public JComboBoxSelectionModel<Account> productIdComboModel = null;
//    public JComboBoxSelectionModel<Account> productNameComboModel = null;
    boolean showComboKeys = false;

    /**
     * Creates new form ReceiveInventoryPanel
     */
    public ProductPrintLabelTable() {
        initComponents();
        
        tableReceiveInv = new RXTable() {
            @Override
            public boolean getScrollableTracksViewportWidth() {
                return getPreferredSize().width < getParent().getWidth();
            }
        };
        
        productCategory = new JGenericComboBoxSelectionModel<String>(panelFilter, ProductCategoryGroupSelectSingleton.getValueList());
        productCategory.jComboBox.addActionListener(new ActionListener() {//add actionlistner to listen for change
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        
        try {
            String uom = ProductCategoryGroupSelectSingleton.getString(ProductCategoryGroupSelectSingleton.LENTILS);
            productCategory.setSelectedItem(uom);
        } catch (Exception ex) {
            Logger.getLogger(ProductPrintLabelTable.class.getName()).log(Level.SEVERE, null, ex);
        }

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

    public ProductCompositeLabelPrint getSelectedAccount() {
        return listModelSelection.getSelection();
    }

    public JTextField getTxtPartyIdTableTextField() {
        return txtCategoryIdTableTextField;
    }

    public RowColumnClickActionTableCellEditor getCategoryActionTableCellEditor() {
        return rowColumnClickActionTableCellEditor;
    }

    final public void setupEditOrderTable() {
        tableReceiveInv.setSelectAllForEdit(true);
        tableReceiveInv.setSurrendersFocusOnKeystroke(true);

        for (int i = 0; i < ProductCompositePrintLabelTableModel.Columns.values().length; i++) {
            ProductCompositePrintLabelTableModel.Columns[] columns = ProductCompositePrintLabelTableModel.Columns.values();
            ProductCompositePrintLabelTableModel.Columns column = columns[i];
            TableColumn col = tableReceiveInv.getColumnModel().getColumn(i);
            col.setPreferredWidth(column.getColumnWidth());

            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                Debug.logError("Date format", "module");
                col.setCellRenderer(new DateFormatCellRenderer());
            } else if (ProductCompositePrintLabelTableModel.Columns.PRODUCTID == column) {
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

    public void clearDialogFields() {

    }

    public void setDialogField() {

        setMemberList(itemListModel);

    }

    public void getDialogField() {
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panelFilter = new javax.swing.JPanel();
        btnAddToPrintList = new javax.swing.JButton();
        btnClearPrintList = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        setMinimumSize(new java.awt.Dimension(0, 200));
        setPreferredSize(new java.awt.Dimension(911, 500));
        setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout panelFilterLayout = new javax.swing.GroupLayout(panelFilter);
        panelFilter.setLayout(panelFilterLayout);
        panelFilterLayout.setHorizontalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 277, Short.MAX_VALUE)
        );
        panelFilterLayout.setVerticalGroup(
            panelFilterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 35, Short.MAX_VALUE)
        );

        btnAddToPrintList.setText("Add To Print List");

        btnClearPrintList.setText("Clear Print List");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAddToPrintList)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnClearPrintList)
                .addContainerGap(409, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAddToPrintList)
                        .addComponent(btnClearPrintList))
                    .addComponent(panelFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.PAGE_START);
        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAddToPrintList;
    public javax.swing.JButton btnClearPrintList;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel panelFilter;
    // End of variables declaration//GEN-END:variables
}
