/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.purchaseorder.inventory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import mvc.controller.LoadReceiveInventoryCompositeWorker;
import mvc.controller.PrintInventoryReceiptStickerAction;
import mvc.controller.ReceiveInventoryGenerateInventoryAction;
import mvc.controller.ReceiveInventoryRejectAllAction;
import mvc.controller.ReceiveInventoryResetAllAction;
import mvc.controller.ReceiveInventorySetReceiveAllAction;
import mvc.model.list.ListAdapterListModel;
import static org.ofbiz.base.container.ClassLoaderContainer.getClassLoader;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.ReceiveInventoryComposite;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.orderbase.ItemListInterface;
import org.ofbiz.ordermax.payment.sales.CustomerPaymentInvoiceController;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.pos.screen.PosScreen;

/**
 *
 * @author siranjeev
 */
public class ReceiveInventoryController extends BaseMainScreen {

    
    static public ReceiveInventoryController runController(ControllerOptions options) {

        ReceiveInventoryController controller = new ReceiveInventoryController(options);
        if (options.getDesktopPane() == null) {
            controller.loadNonSizeableTwoPanelDialogScreen(ReceiveInventoryController.module, options.getDesktopPane(), null);
        } else {
            controller.loadTwoPanelInternalFrame(ReceiveInventoryController.module, options.getDesktopPane());
        }
        return controller;
    }    
    
    public static final String module = ReceiveInventoryController.class.getName();
    public ReceiveInventoryPanel panel = null;
    public final String caption = "Recevive Inventory";
    public PosScreen pos = null;
    final ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();

    
    
//    private Order order = null;
//    public Order getOrder() {
//        return order;
//    }
    public void setOrder(Order order) {
        //      this.order = order;
        orderListModel.clear();
        orderListModel.add(order);
    }

    public ReceiveInventoryController(ControllerOptions options) {
        super( ControllerOptions.getSession());
        Order order = (Order) options.get("Order");
        if(order!=null){
            orderListModel.add(order);            
        }
    }


    SimpleInventoryReceiptButtonPanel buttonPanel = null;
    private ListAdapterListModel<ShipmentReceiptComposite> ricListModel = new ListAdapterListModel<ShipmentReceiptComposite>();

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new ReceiveInventoryPanel();
        panel.setOrderList(orderListModel);

        panel.selectionModel.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                final ClassLoader cl = getClassLoader();
                Thread.currentThread().setContextClassLoader(cl);

                if (!event.getValueIsAdjusting()) {
                    ricListModel.clear();
                    Order ord = (Order) panel.ordersComboBox.getSelectedItem();
                    ReceiveInventoryComposite ric = LoadReceiveInventoryCompositeWorker.getShipmentReceiptComposite(ord, ControllerOptions.getSession());
                    for (ShipmentReceiptComposite iter : ric.getShipmentReceiptCompositeList().getList()) {
                        iter.setOrder(ord);
                        ricListModel.add(iter);
                    }
                    panel.setReceiveInventoryList(ricListModel);
                    panel.lblShipmentId.setText("#" + ric.getShipment().getshipmentId());
                }
            }
        });


       if (orderListModel.getSize() > 0) {
            panel.ordersComboBox.setSelectedIndex(0);
        }        
        createTreePanel();
        f.setDividerLocation(0);

        buttonPanel = new SimpleInventoryReceiptButtonPanel();
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());

        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
            }
        });

        panel.btnReceiveAll.addActionListener(new ReceiveInventorySetReceiveAllAction(ricListModel));
        panel.btnClearAll.addActionListener(new ReceiveInventoryResetAllAction(ricListModel));
        panel.btnRejectAll.addActionListener(new ReceiveInventoryRejectAllAction(ricListModel));
        ReceiveInventoryGenerateInventoryAction receiveGenAction = new ReceiveInventoryGenerateInventoryAction(ricListModel, ControllerOptions.getSession());
        receiveGenAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ActionEvent event = new ActionEvent(this, 1, "receive inventory");
                for (ActionListener listener : listeners) {
                    listener.actionPerformed(event); // broadcast to all
                }
                f.okButtonPressed();

            }
        });
        buttonPanel.btnGenerateInventory.addActionListener(receiveGenAction);
    }

    @Override
    public String getClassName() {
        return module;
    }
}
