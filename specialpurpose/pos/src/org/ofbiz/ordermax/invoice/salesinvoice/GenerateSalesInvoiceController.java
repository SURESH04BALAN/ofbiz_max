/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.invoice.salesinvoice;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static javax.swing.Action.NAME;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import mvc.controller.LoadPurchaseOrderWorker;
import mvc.controller.OrderChangeStatusToApprovedAction;
import mvc.controller.PrintOrderPickingSlipAction;
import mvc.controller.PrintPurchaseInvoiceAction;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.invoice.GenerateInvoiceButtonPanel;
import org.ofbiz.ordermax.invoice.InteractiveForm;
import org.ofbiz.ordermax.invoice.InteractiveTableModel;
import org.ofbiz.ordermax.invoice.LoadInvoiceWorker;
import org.ofbiz.ordermax.invoice.OrderInvoiceGenerateRecord;
import org.ofbiz.ordermax.orders.OrderFindHelper;
import org.ofbiz.ordermax.party.PartyListSingleton;
import org.ofbiz.ordermax.screens.action.PurchaseOrderAction;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.pos.screen.PosScreen;

/**
 *
 * @author siranjeev
 */
public class GenerateSalesInvoiceController extends BaseMainScreen {

    public static final String module = GenerateSalesInvoiceController.class.getName();
    public InteractiveForm panel = null;
    public final String caption = "Receive Inventory";
    public PosScreen pos = null;
    private Order order = null;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public GenerateSalesInvoiceController( XuiSession sess) {
        super(sess);

    }

    public GenerateSalesInvoiceController(ListAdapterListModel<Order> ordListModel, XuiSession sess) {
        super( sess);
        this.orderListModel.addAll(ordListModel.getList());
    }

    GenerateInvoiceButtonPanel buttonPanel = null;
//    org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController.CopyToPopupButton copyToButton = null;
//    private ListAdapterListModel<Order> ricListModel = new ListAdapterListModel<Order>();
    final ListAdapterListModel<InvoiceComposite> invoicesListModel = new ListAdapterListModel<InvoiceComposite>();
    final ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        width = 700;
        height = 600;

        panel = new InteractiveForm(orderListModel, ControllerOptions.getSession(), ControllerOptions.getDesktopPane());//new GeneratePurchaseInvoicePanel();
        TableColumn hidden = panel.getTable().getColumnModel().getColumn(InteractiveTableModel.HIDDEN_INDEX);
        hidden.setMinWidth(2);
        hidden.setPreferredWidth(2);
        hidden.setMaxWidth(2);
        hidden.setCellRenderer(new InteractiveRenderer(InteractiveTableModel.HIDDEN_INDEX));

        /*    ReceiveInventoryComposite ric = LoadReceiveInventoryCompositeWorker.getShipmentReceiptComposite(order, ControllerOptions.getSession());
         for (ShipmentReceiptComposite iter : ric.getShipmentReceiptCompositeList().getList()) {
         ricListModel.add(iter);
         }
         */
        //      ricListModel.add(new Order("PURCHASE_ORDER"));
//        panel.setReceiveInventoryList(ricListModel);
//        panel.lblurchaseOrderId.setText("#"+ric.getOrder().getOrderHeader().getOrderId());
//        panel.lblShipmentId.setText("#"+ric.getShipment().getshipmentId());   
        createTreePanel();
        f.setDividerLocation(0);
//        panel.setProductListArray((ProductListArray) treePanel.getTreeDataList());

//        panel.newItem();
//        panel.addChangeListener(this);
        buttonPanel = new GenerateInvoiceButtonPanel();
//        copyToButton = new PurchaseOrderController.CopyToPopupButton(buttonPanel.getCopyToButton());
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(treePanel.getContainerPanel(), f.getPanelSelecton());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());

//        buttonPanel.getCancelButton().addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                cancelButtonPressed();
//            }
//        });
  /*      
         buttonPanel.btnDisplayOrder.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
         String url = "https://localhost:8443/ordermgr/control/orderview?orderId=" + order.getOrderHeader().getOrderId();
         try {            
         java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
         } catch (IOException ex) {
         Logger.getLogger(ReceiveInventoryGenerateInventoryAction.class.getName()).log(Level.SEVERE, null, ex);
         }  

         }
         });
         */
        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //              panel.getDialogFields();
                //              Order order = panel.getOrder();
                //              SavePurchaseOrderWorker saveOrderWorker = new SavePurchaseOrderWorker(order, ControllerOptions.getSession());;
                Debug.logInfo("before loadPersonsWorker.execute", NAME);
//                saveOrderWorker.execute();
                //               SavePurchaseOrderWorker.saveOrderCart(order, ControllerOptions.getSession());
                Debug.logInfo("after loadPersonsWorker.execute", NAME);

                //update the dialog
//                panel.setDialogFields();
                f.okButtonPressed();
            }
        });

        buttonPanel.getApproveAllButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OrderChangeStatusToApprovedAction statusApprovedAction = new OrderChangeStatusToApprovedAction(null, ControllerOptions.getSession());
                List<OrderInvoiceGenerateRecord> list = panel.getDataList();
                for (OrderInvoiceGenerateRecord item : list) {
                    statusApprovedAction.setOrder(item.getOrder());
                    statusApprovedAction.actionPerformed(e);
                }
                invoicesListModel.clear();

                orderListModel.clear();
                for (OrderInvoiceGenerateRecord item : list) {
                    orderListModel.add(item.getOrder());
                }

                loadAllInvoices();
            }
        });

        PrintPurchaseInvoiceAction printPurchaseInvoiceAction = new PrintPurchaseInvoiceAction(invoicesListModel, ControllerOptions.getSession());
        buttonPanel.getPrintAllInvoiceButton().addActionListener(printPurchaseInvoiceAction);

        PrintOrderPickingSlipAction printPickingSlipAction = new PrintOrderPickingSlipAction(orderListModel, ControllerOptions.getSession());
        buttonPanel.btnPrintPickingSlip.addActionListener(printPickingSlipAction);
//        ReceiveInventorySetReceiveAllAction allAction = new ReceiveInventorySetReceiveAllAction(ricListModel);
//        buttonPanel.btnReceiveAllInventory.addActionListener(allAction);

//        ReceiveInventoryResetAllAction allResetAction = new ReceiveInventoryResetAllAction(ricListModel);
//        buttonPanel.btnGenerateInvoice.addActionListener(allResetAction);
//        ReceiveInventoryGenerateInventoryAction generateInventoryAction = new ReceiveInventoryGenerateInventoryAction(ricListModel, order,ControllerOptions.getSession());
//        buttonPanel.btnGenerateInventory.addActionListener(generateInventoryAction);
//        ReceiveInventoryGenerateInvoiceAction generateInvoice = new ReceiveInventoryGenerateInvoiceAction(ricListModel, order,ControllerOptions.getSession());
//               buttonPanel.btnGenerateInvoice.addActionListener(generateInvoice);
    }

    void loadAllInvoices() {
        invoicesListModel.clear();
        Set<String> invoiceList = new HashSet<String>();

        List<OrderInvoiceGenerateRecord> list = panel.getDataList();
        for (OrderInvoiceGenerateRecord item : list) {
            invoiceList.addAll(item.getOrder().getInvoiceIds());
        }

        invoicesListModel.addAll(LoadInvoiceWorker.loadPurchaseOrderComposites(invoiceList,
                ControllerOptions.getSession()));
    }

    class InteractiveRenderer extends DefaultTableCellRenderer {

        protected int interactiveColumn;

        public InteractiveRenderer(int interactiveColumn) {
            this.interactiveColumn = interactiveColumn;
        }

        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus, int row,
                int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (column == interactiveColumn && hasFocus) {
                try {
                    String orderId = (String) panel.getTableModel().getValueAt(row, InteractiveTableModel.ORDER_ID);
                    Debug.logInfo("orderId: " + orderId + " row: " + row, "interactive");
                    GenericValue orderGV = OrderFindHelper.getOrder(orderId, ControllerOptions.getSession().getDelegator());
                    if (orderGV != null) {

                        OrderInvoiceGenerateRecord audioRecord = (OrderInvoiceGenerateRecord) panel.getTableModel().getRowData(row);
                        if (audioRecord != null) {
                            if (audioRecord.getOrder() == null || (audioRecord.getOrder() != null
                                    && audioRecord.getOrder().getOrderId().equals(orderId) == false)) {

                                Order order = LoadPurchaseOrderWorker.loadOrder(orderId, ControllerOptions.getSession());
//                            orderListModel.add(order);

                                //get invoices
                                audioRecord.getInvoicesListModel().clear();
                                audioRecord.getInvoicesListModel().addAll(LoadInvoiceWorker.loadPurchaseOrderComposites(order.getInvoiceIds(),  ControllerOptions.getSession()));

                                //set details
                                audioRecord.setOrder(order);
                                try {
                                    audioRecord.setPartyName(PartyListSingleton.getAccount(order.getPartyId()).getDisplayName());
                                } catch (Exception ex) {
                                }

                                audioRecord.setOrderStatus(order.getOrderStatusId());

                                //set button
                                PurchaseOrderAction purchaserAction = new PurchaseOrderAction(orderId, "Purchaser Order", ControllerOptions.getSession(), ControllerOptions.getDesktopPane());
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

                                reloadLists();
                                /*
                                 if (audioRecord.getOrderButton() == null) {
                                 PurchaseOrderAction purchaserAction = new PurchaseOrderAction(orderId, "Purchaser Order", ControllerOptions.getSession(), desktopPane);
                                 JButton btn = purchaserAction.createActionButton();
                                 audioRecord.setOrderButton(btn);//.setAction(purchaserAction);
                                 }
                                 */
                            }

                        }
                    }
                } catch (Exception ex) {
                    Debug.logError(ex, "Error");
                }
                if ((panel.getTableModel().getRowCount() - 1) == row
                        && !panel.getTableModel().hasEmptyRow()) {
                    panel.getTableModel().addEmptyRow();
                    table.setRowHeight(row, 25);
                    table.setRowHeight(row + 1, 25);
                }
                panel.highlightLastRow(row);
            }

            return c;
        }
    }

    public void reloadLists() {
        orderListModel.clear();
        invoicesListModel.clear();
        List<OrderInvoiceGenerateRecord> list = panel.getTableModel().getDataList();
        for (OrderInvoiceGenerateRecord oigr : list) {
            orderListModel.add(oigr.getOrder());
            invoicesListModel.addAll(oigr.getInvoicesListModel().getList());
        }
    }

    @Override
    public String getClassName() {
        return module;
    }

}
