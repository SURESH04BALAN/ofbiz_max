/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns.receivereturn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import mvc.controller.ReceiveInventoryGenerateInventoryAction;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.orderbase.returns.ReturnItemComposite;
import org.ofbiz.ordermax.utility.OrderMaxUtility;
import org.ofbiz.pos.screen.PosScreen;

/**
 *
 * @author siranjeev
 */
public class ReceiveReturnInventoryController extends BaseMainScreen {

    static public ReceiveReturnInventoryController runController(ControllerOptions options) {

        ReceiveReturnInventoryController controller = new ReceiveReturnInventoryController(options);
        if (options.getDesktopPane() == null) {
            controller.loadNonSizeableTwoPanelDialogScreen(ReceiveReturnInventoryController.module, options.getDesktopPane(), null);
        } else {
            controller.loadTwoPanelInternalFrame(ReceiveReturnInventoryController.module, options.getDesktopPane());
        }
        return controller;
    }

    public static final String module = ReceiveReturnInventoryController.class.getName();
    public ReceiveOrderReturnItemsListPanel panel = null;
    public final String caption = "Recevive Inventory";
    public PosScreen pos = null;
    final ListAdapterListModel<ReturnHeaderComposite> orderListModel = new ListAdapterListModel<ReturnHeaderComposite>();

//    private Order order = null;
//    public Order getOrder() {
//        return order;
//    }
    ControllerOptions options = null;
    ReturnHeaderComposite returnHeaderComposite = null;

    public ReceiveReturnInventoryController(ControllerOptions options) {
        super(ControllerOptions.getSession());
        this.options = options;
        returnHeaderComposite = (ReturnHeaderComposite) options.get("ReturnHeaderComposite");
    }

    SimpleReceiveReturnButtonPanel buttonPanel = null;
    private ListAdapterListModel<ReturnItemComposite> ricListModel = new ListAdapterListModel<ReturnItemComposite>();

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);

        panel = new ReceiveOrderReturnItemsListPanel(options);
        if (returnHeaderComposite != null) {
            ricListModel.addAll(returnHeaderComposite.getOrderReturnItemsList().getList());
            panel.reloadItemDataModel(ricListModel);
        }
        /*
         panel.selectionModel.addListSelectionListener(new ListSelectionListener() {
         public void valueChanged(ListSelectionEvent event) {
         final ClassLoader cl = getClassLoader();
         Thread.currentThread().setContextClassLoader(cl);

         if (!event.getValueIsAdjusting()) {
         ricListModel.clear();
         Order ord = (Order) panel.ordersComboBox.getSelectedItem();
         ReceiveInventoryComposite ric = LoadReceiveInventoryCompositeWorker.getShipmentReceiptComposite(ord, session);
         for (ShipmentReceiptComposite iter : ric.getShipmentReceiptCompositeList().getList()) {
         iter.setOrder(ord);
         ricListModel.add(iter);
         }
         panel.setReceiveInventoryList(ricListModel);
         panel.lblShipmentId.setText("#" + ric.getShipment().getshipmentId());
         }
         }
         });
         */

        createTreePanel();
        f.setDividerLocation(0);

        buttonPanel = new SimpleReceiveReturnButtonPanel();
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());

        buttonPanel.getOkButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
            }
        });

//        panel.btnReceiveAll.addActionListener(new ReceiveInventorySetReceiveAllAction(ricListModel));
//        panel.btnClearAll.addActionListener(new ReceiveInventoryResetAllAction(ricListModel));
//        panel.btnRejectAll.addActionListener(new ReceiveInventoryRejectAllAction(ricListModel));
        ReceiveOrderReturnItemsInventoryAction receiveGenAction = new ReceiveOrderReturnItemsInventoryAction(options, returnHeaderComposite);
        receiveGenAction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ActionEvent event = new ActionEvent(this, 1, "receive inventory");
                for (ActionListener listener : listeners) {
                    listener.actionPerformed(event); // broadcast to all
                }
                f.okButtonPressed();
            }
        });
        
        buttonPanel.btnReceiveInventory.addActionListener(receiveGenAction);
                
    }

    @Override
    public String getClassName() {
        return module;
    }
}
