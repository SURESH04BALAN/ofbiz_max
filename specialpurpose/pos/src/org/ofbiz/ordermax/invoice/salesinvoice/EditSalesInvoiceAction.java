/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.invoice.salesinvoice;

import mvc.controller.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Action;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.OrderMaxOptionPane;
import org.ofbiz.ordermax.composite.InvoiceComposite;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;
import org.ofbiz.ordermax.orderbase.OrderActionInterface;
import org.ofbiz.ordermax.orderbase.returns.OrderReturnActionInterface;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;

/**
 *
 * @author siranjeev
 */
public class EditSalesInvoiceAction extends ScreenAction {

    static public final String nameStr = "Sales Invoice";
    final String iconPathStr = "";//"clients.png";
    final String iconPathSmallStr = "";//"clients.png";
    private OrderActionInterface orderActionInterface;
    private OrderReturnActionInterface orderReturnActionInterface;

    private ListAdapterListModel<InvoiceComposite> invoiceCompListModel = null;

    public EditSalesInvoiceAction(javax.swing.JDesktopPane desktopPane, OrderActionInterface orderActInterface) {
        super(ActionType.EDIT_SALES_INVOICE_ACTION,
                EditSalesInvoiceAction.nameStr, XuiContainer.getSession(), desktopPane);
        this.orderActionInterface = orderActInterface;
    }

    public EditSalesInvoiceAction(javax.swing.JDesktopPane desktopPane, OrderReturnActionInterface orderReturnActionInterface) {
        super(ActionType.EDIT_SALES_INVOICE_ACTION,
                EditSalesInvoiceAction.nameStr, XuiContainer.getSession(), desktopPane);
        this.orderReturnActionInterface = orderReturnActionInterface;
    }

    public EditSalesInvoiceAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane, ListAdapterListModel<InvoiceComposite> invoiceCompListModel) {
        super(ActionType.EDIT_SALES_INVOICE_ACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
        this.invoiceCompListModel = invoiceCompListModel;
        this.session = session;
    }

    public EditSalesInvoiceAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.EDIT_SALES_INVOICE_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public EditSalesInvoiceAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.EDIT_SALES_INVOICE_ACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.orderActionInterface != null) {
            for (String invoiceId : orderActionInterface.getOrder().getInvoiceIds()) {
                SalesInvoiceController findOrderListMain = new SalesInvoiceController(invoiceId, session);
                findOrderListMain.loadTwoPanelInternalFrame(SalesInvoiceController.module, desktopPane);
            }
        } else if (this.orderReturnActionInterface != null) {
            ReturnHeaderComposite returnComp = orderReturnActionInterface.getOrderReturn();//getInvoice()
            if (returnComp.getReturnInvoice() != null) {
                String invoiceId = returnComp.getReturnInvoice().getinvoiceId();
                SalesInvoiceController findOrderListMain = new SalesInvoiceController(invoiceId, session);
                findOrderListMain.loadTwoPanelInternalFrame(SalesInvoiceController.module, desktopPane);
            }
        } else if (invoiceCompListModel == null) {
            SalesInvoiceController findOrderListMain = new SalesInvoiceController(session);
            findOrderListMain.loadTwoPanelInternalFrame(SalesInvoiceController.module, desktopPane);
        } else {
            if (invoiceCompListModel.getSize() > 0) {

                for (final InvoiceComposite invComposite : invoiceCompListModel.getList()) {

                    try {
                        SalesInvoiceController salesOrder = new SalesInvoiceController(invComposite.getInvoice().getinvoiceId(), session);
                        salesOrder.loadTwoPanelInternalFrame(SalesInvoiceController.module, desktopPane);
                    } //          });
                    catch (Exception ex) {
                        Logger.getLogger(PrintSalesInvoiceAction.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                    }
                }
            } else {
                int selection = OrderMaxOptionPane.showConfirmDialog(
                        null, "Invoice is not generated yet. Please approve the order", "Invoice : ", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    @Override
    public Action getAction() {
        return this;
    }

    static class LoadOrderListActionFactory implements ScreenClassFactoryInterface {

        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new EditSalesInvoiceAction(name, session, desktopPane, null);
        }

        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
            return new EditSalesInvoiceAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.ORDER_LIST_ACTION, new LoadOrderListActionFactory());
    }

}
