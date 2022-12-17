/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.orderviews;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import mvc.model.list.ListAdapterListModel;
import mvc.model.table.OrderSummaryTableModel;
import mvc.view.GenericTableModelPanel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.cellrenderer.DateFormatCellRenderer;
import org.ofbiz.ordermax.composite.OrderSummaryView;
import org.ofbiz.ordermax.orderbase.ProductTreeActionTableCellEditor;
import org.ofbiz.ordermax.orderbase.RowColumnActionEvent;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.orderbase.returns.MaintainOrderReturnController;
import org.ofbiz.ordermax.orderbase.salesorder.SalesOrderController;
import org.ofbiz.ordermax.payment.purchase.SupplierPaymentInvoiceController;
import org.ofbiz.ordermax.payment.sales.CustomerPaymentInvoiceController;
import org.ofbiz.ordermax.pdf.SalesRegisterUpdatePanel;

/**
 *
 * @author BBS Auctions
 */
public class OrderDetailViewTablePanel extends javax.swing.JPanel {

    public static final String module = OrderDetailViewTablePanel.class.getName();
    public GenericTableModelPanel<OrderSummaryView, OrderSummaryTableModel> tablePanel = null;
    private final JTextField txtOrderIdTableTextField = new JTextField();
    private ProductTreeActionTableCellEditor productTreeActionTableCellEditor = null;

    /**
     * Creates new form OrderDetailView
     */
    public OrderDetailViewTablePanel() {
        initComponents();
        tablePanel = new GenericTableModelPanel<OrderSummaryView, OrderSummaryTableModel>(new OrderSummaryTableModel());
        this.setLayout(new BorderLayout());
        this.add(BorderLayout.CENTER, tablePanel);
        setupEditOrderTable();

    }

    final public void setupEditOrderTable() {
        tablePanel.jTable.setSurrendersFocusOnKeystroke(true);
        txtOrderIdTableTextField.setBorder(BorderFactory.createEmptyBorder());
        DefaultCellEditor editor = new DefaultCellEditor(txtOrderIdTableTextField);
        editor.setClickCountToStart(0);
        productTreeActionTableCellEditor = new ProductTreeActionTableCellEditor(editor);
        tablePanel.jTable.getColumn("Number").setCellEditor(productTreeActionTableCellEditor);

        for (int i = 0; i < OrderSummaryTableModel.Columns.values().length; i++) {
            OrderSummaryTableModel.Columns[] columns = OrderSummaryTableModel.Columns.values();
            OrderSummaryTableModel.Columns column = columns[i];
            TableColumn col = tablePanel.jTable.getColumnModel().getColumn(i);

            col.setPreferredWidth(column.getColumnWidth());

            if (column.getClassName().equals(BigDecimal.class)) {
                col.setCellRenderer(new org.ofbiz.ordermax.cellrenderer.DecimalFormatRenderer());
            } else if (column.getClassName().equals(java.sql.Timestamp.class)) {
                col.setCellRenderer(new DateFormatCellRenderer());
            }

            Font font = tablePanel.jTable.getFont();
            font = font.deriveFont((float) (font.getSize2D() * 1.04));
            tablePanel.jTable.setFont(font);
            txtOrderIdTableTextField.setFont(font);
            tablePanel.jTable.setRowHeight(tablePanel.jTable.getRowHeight() + 1);
        }

        ActionListener invoiceIdChangeAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Debug.logInfo("e: " + e.toString(), module);
                if (e.getSource() instanceof ProductTreeActionTableCellEditor
                        && e instanceof RowColumnActionEvent) {

                    ProductTreeActionTableCellEditor lookupCellEditor = (ProductTreeActionTableCellEditor) e.getSource();
                    RowColumnActionEvent event = (RowColumnActionEvent) e;
                    int modelRow = tablePanel.jTable.convertRowIndexToModel(event.getRow());
                    OrderSummaryView orderSummaryView = tablePanel.listModel.getElementAt(modelRow);
                    if (orderSummaryView.getLineItemType() == OrderSummaryView.LineItemType.OrderLineItem) {
                        ControllerOptions cOptions = new ControllerOptions();
                        if ("SALES_ORDER".equals(orderSummaryView.getOrderTypeId())) {

                            cOptions.addRoleType("CUSTOMER");
                            cOptions.addOrderId(orderSummaryView.getNumber());
                            SalesOrderController.runController(cOptions);// salesOrderController = new SalesOrderController(orderId, cOptions,  session);

                            //     f.okButtonPressed();
                        } else {
                            cOptions.addRoleType("SUPPLIER");
                            cOptions.put("orderId", orderSummaryView.getNumber());
                            PurchaseOrderController.runController(cOptions);// purchaseOrderController = new PurchaseOrderController(orderId, cOptions, null, session);

                        }
                    } else if (orderSummaryView.getLineItemType() == OrderSummaryView.LineItemType.OrderReturnLineItem) {
                        ControllerOptions option = new ControllerOptions();
                        option.addReturnId(orderSummaryView.getNumber());
                        if ("SALES_ORDER".equals(orderSummaryView.getOrderTypeId())) {
                            option.addParentPaymentTypeId("RECEIPT");
                        } else {
                            option.addParentPaymentTypeId("DISBURSEMENT");
                        }
                        MaintainOrderReturnController.runController(option);
                    } else if (orderSummaryView.getLineItemType() == OrderSummaryView.LineItemType.PaymentLineItem) {
                        String paymentId = orderSummaryView.getNumber();
                        ControllerOptions option = new ControllerOptions();
                        option.addPaymentId(paymentId);
                        Debug.logError("paymentId: " + paymentId, module);
                        if ("SALES_ORDER".equals(orderSummaryView.getOrderTypeId())) {
                            CustomerPaymentInvoiceController.runController(option);// paymentController = new CustomerPaymentInvoiceController(option);
                            //paymentController.loadTwoPanelInternalFrame(PaymentController.module, ControllerOptions.getDesktopPane());
                        } else {
                            SupplierPaymentInvoiceController.runController(option);
                            //SupplierPaymentInvoiceController paymentController = new SupplierPaymentInvoiceController(paymentId, session);
                            //paymentController.loadTwoPanelInternalFrame(PaymentController.module, ControllerOptions.getDesktopPane());
                        }

                    } else if (orderSummaryView.getLineItemType() == OrderSummaryView.LineItemType.InvoiceLineItem) {
                        try {
                            String invoiceId = orderSummaryView.getNumber();
                            ControllerOptions option = new ControllerOptions();
                            option.addInvoiceId(invoiceId);
                            GenericValue orderValue = PosProductHelper.getGenericValueByKey("Invoice", UtilMisc.toMap("invoiceId", invoiceId), XuiContainer.getSession().getDelegator());
                            /* if ("PURCHASE_INVOICE".equals(orderValue.getString("invoiceTypeId"))) {
                             PurchaseInvoiceController purchaseOrder = new PurchaseInvoiceController(invoiceId, XuiContainer.getSession());
                             purchaseOrder.loadTwoPanelInternalFrame(OrderDetailViewTablePanel.module, ControllerOptions.getDesktopPane());
                             } else {
                             SalesInvoiceController salesOrder = new SalesInvoiceController(invoiceId, XuiContainer.getSession());
                             salesOrder.loadTwoPanelInternalFrame(OrderDetailViewTablePanel.module, ControllerOptions.getDesktopPane());
                             }
                             */
                            /*if (Desktop.isDesktopSupported()) {
                             try {
                             String path = ProductListArray.InvoicePdfFilePath;
                             if(UtilValidate.isNotEmpty(orderSummaryView.getPartyId())){
                             path =  path.concat(orderSummaryView.getPartyId()).concat("\\");
                             }
                             path = path.concat(invoiceId).concat(".pdf");
                             File myFile = new File(path);//new File("C:\\ofbiz\\ofbiz-12.04.02\\applications\\content\\data\\pdftest.pdf");
                             Desktop.getDesktop().open(myFile);
                             } catch (IOException ex) {
                             //e
                             }
                                
                             }*/

                            JFrame applicationFrame = new JFrame();
                           // applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            SalesRegisterUpdatePanel viewerComponentPanel = new SalesRegisterUpdatePanel(orderSummaryView.getDetails());
                            applicationFrame.getContentPane().add(viewerComponentPanel);
                            // Now that the GUI is all in place, we can try openning a PDF
                            //controller.openDocument(filePath);
                            // show the component
                            applicationFrame.pack();
                            applicationFrame.setVisible(true);
                        } catch (Exception ex) {
                            Debug.logError(ex, module);
                        }
                    }
                    /*
                     try {
                     String invoiceId = txtOrderIdTableTextField.getText();
  
                     GenericValue orderValue = PosProductHelper.getGenericValueByKey("Invoice", UtilMisc.toMap("invoiceId", invoiceId), delegator);
                     if ("PURCHASE_INVOICE".equals(orderValue.getString("invoiceTypeId"))) {
                     PurchaseInvoiceController purchaseOrder = new PurchaseInvoiceController(invoiceId, null, session);
                     purchaseOrder.loadTwoPanelInternalFrame(PurchaseOrderController.module, desktopPane, null);
                     } else {
                     SalesInvoiceController salesOrder = new SalesInvoiceController(invoiceId, null, session);
                     salesOrder.loadTwoPanelInternalFrame(SalesInvoiceController.module, desktopPane, null);
                     }
                     } catch (Exception ex) {
                     Debug.logError(ex, module);
                     }*/
                    /*
                     Map<String, Object> genVal = ProductSelectionPanel.getUserProductSelection(ProductTreeArraySingleton.getInstance());//getUserPartyUserSelection();
                     if (genVal != null) {
                     if (genVal.containsKey("productId")) {

                     if (panel != null) {

                     JTextField textField = panel.getTxtProdIdTableTextField();
                     if (textField != null) {
                     textField.setText(genVal.get("productId").toString());
                     Debug.logWarning("Order changed - product id text field: " + genVal.get("productId").toString(), module);
                     //                                        processProductIdTextFieldChange(textField, event.getRow());
                     event.getTable().setValueAt(genVal.get("productId").toString(), event.getRow(), OrderEntryTableModel.ORDER_PROD_ID_INDEX);
                     event.getTable().changeSelection(event.getRow(), OrderEntryTableModel.ORDER_PROD_INTERNALNAME_INDEX, false, false);
                     }
                     } else {
                     //                        table.setValueAt(genVal.get("productId").toString(), row, column);
                     }
                     }
                     }
                     */

                }
            }
        };

        productTreeActionTableCellEditor.addActionListener(invoiceIdChangeAction);

    }

    public void setListModal(ListAdapterListModel<OrderSummaryView> list) {
        tablePanel.setListModel(list);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
