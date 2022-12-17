/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.invoice.salesinvoice;

import org.ofbiz.ordermax.invoice.purchaseinvoice.*;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.invoice.purchaseinvoice.GeneratePurchaseInvoiceController;
import org.ofbiz.ordermax.orderbase.OrderIdInterface;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;

/**
 *
 * @author siranjeev
 */
public class GenerateSalesInvoiceAction extends ScreenAction {

    public static final String module = GenerateSalesInvoiceAction.class.getName();
    public static final String nameStr = "Generate Sales Invoices";
    final String iconPathStr = "";//sales.png";
    final String iconPathSmallStr = "";//"salesorder_small.png";
    private OrderIdInterface orderIdInterface = null;

    public GenerateSalesInvoiceAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, 
            OrderIdInterface orderIdInterface) {
        super(ActionType.GENERATE_PURCHASE_INVOICE_FROM_ORDERACTION, name, session, desktopPane);

        this.orderIdInterface = orderIdInterface;
        this.session = session;
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public GenerateSalesInvoiceAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.GENERATE_PURCHASE_INVOICE_FROM_ORDERACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public GenerateSalesInvoiceAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.GENERATE_PURCHASE_INVOICE_FROM_ORDERACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }



    ListAdapterListModel<Order> orderListModel = new ListAdapterListModel<Order>();

    public GenerateSalesInvoiceAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane,  ListAdapterListModel<Order> orderListModel) {
        super(ActionType.GENERATE_PURCHASE_INVOICE_FROM_ORDERACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
//        Debug.logInfo("Size 1: " + orderListModel.getSize() , module);        
        this.orderListModel = orderListModel;
        loadIcons();
    }

    public GenerateSalesInvoiceAction(String name, XuiSession session) {
        super(ActionType.GENERATE_PURCHASE_INVOICE_FROM_ORDERACTION, session);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }
    private Order order = null;

    @Override
    public void actionPerformed(ActionEvent e) {

//        if (order != null ) {
        Debug.logInfo("Size : " + orderListModel.getSize(), module);
        GenerateSalesInvoiceController salesInvoiceOrder = null;
        if (orderIdInterface != null) {
            salesInvoiceOrder = new GenerateSalesInvoiceController(orderListModel, session);
        } else {
            salesInvoiceOrder = new GenerateSalesInvoiceController(orderListModel, session);
        }
//        purchaseOrder.setOrder(order);
        salesInvoiceOrder.loadTwoPanelInternalFrame(GeneratePurchaseInvoiceController.module, desktopPane);
//        }

    }

    @Override
    public Action getAction() {
        return this;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrderList(Order order) {
        this.order = order;
        orderListModel.clear();
        orderListModel.add(order);
    }

    static class ListPriceLookupActionFactory implements ScreenClassFactoryInterface {

        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new GenerateSalesInvoiceAction(name, session, desktopPane);
        }


        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
            return new GenerateSalesInvoiceAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.GENERATE_PURCHASE_INVOICE_FROM_ORDERACTION, new ListPriceLookupActionFactory());
    }
}
