/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.deliveryschedule;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.KeyStroke;
import mvc.controller.LoadOrderWorker;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.BaseMainScreen;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.entity.OrderDeliverySchedule;
import org.ofbiz.ordermax.entity.OrderHeaderAndRoleSummary;

import org.ofbiz.ordermax.utility.OrderMaxUtility;

/**
 *
 * @author siranjeev
 */
public class EditDeliveryScheduleController extends BaseMainScreen {

    public static final String module = EditDeliveryScheduleController.class.getName();
    public EditDeliverySchedulePanel panel = null;
    public final String caption = "Party List";
    final ListAdapterListModel<OrderHeaderAndRoleSummary> orderListModel = new ListAdapterListModel<OrderHeaderAndRoleSummary>();

    public String getCaption() {

        if (controllerOptions.isSalesOrder()) {
            return "Sales Order List";
        } else {
            return "Purchase Order List";
        }

    }

    @Override
    protected void setSizeAndLocation(JInternalFrame frame) {
        int y = 30;
        int x = 100;
        if (ControllerOptions.getDesktopPane() != null) {
            frame.setSize(ControllerOptions.getDesktopPane().getBounds().width - 2 * x, ControllerOptions.getDesktopPane().getBounds().height - 2 * y);
        }
        frame.setLocation(x, y);
    }
    ControllerOptions controllerOptions = null;

    public EditDeliveryScheduleController(ControllerOptions options, XuiSession sess) {
        super(sess);
        controllerOptions = options;
    }

    public EditDeliveryScheduleController(ListAdapterListModel<OrderHeaderAndRoleSummary> ordListModel, ControllerOptions options, XuiSession sess) {
        super(sess);
        orderListModel.addAll(ordListModel.getList());
        controllerOptions = options;
    }

    Order order = null;

    public EditDeliveryScheduleController(Order order, ControllerOptions options, XuiSession sess) {
        super(sess);

        controllerOptions = options;
        this.order = order;
    }

    EditDeliveryScheduleButtonPanel buttonPanel = null;
    final private ListAdapterListModel<OrderHeaderAndRoleSummary> invoiceCompositeListModel = new ListAdapterListModel<OrderHeaderAndRoleSummary>();

    @Override
    public void loadScreen(final ContainerPanelInterface f) {
        final ClassLoader cl = getClassLoader();
        Thread.currentThread().setContextClassLoader(cl);
        width = 800;
        height = 650;
        panel = new EditDeliverySchedulePanel(controllerOptions);

        buttonPanel = new EditDeliveryScheduleButtonPanel();
//        copyToButton = new PurchaseOrderController.CopyToPopupButton(buttonPanel.getCopyToButton());
        OrderMaxUtility.addAPanelGrid(panel, f.getPanelDetail());
        OrderMaxUtility.addAPanelGrid(buttonPanel, f.getPanelButton());

        buttonPanel.btnOk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
            }
        });

        buttonPanel.btnNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
            }
        });

        buttonPanel.btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                f.cancelButtonPressed();
            }
        });

        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        String enterName = "enter";
        InputMap inputMap = panel.getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
//        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enterName);
        ActionMap actionMap = panel.getRootPane().getActionMap();

        actionMap.put(cancelName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                f.cancelButtonPressed();
            }
        });

        actionMap.put(enterName, new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                f.okButtonPressed();
            }
        });
        if (order != null && order.orderHeader != null) {
            OrderDeliverySchedule orderDeliverySchedule = LoadOrderWorker.loadOrderDeliverySchedule(order.orderHeader.getOrderId(), ControllerOptions.getSession());
            panel.setOrderDeliverySchedule(orderDeliverySchedule);
            panel.setDialogFields();
        }
    }

    @Override
    public String getClassName() {
        return module;
    }
}
