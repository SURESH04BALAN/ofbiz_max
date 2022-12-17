/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.invoice.purchaseinvoice;

import org.ofbiz.ordermax.orderbase.FindOrderListController;
import org.ofbiz.ordermax.orderbase.*;
import mvc.controller.*;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.HashSet;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.KeyStroke;
import mvc.data.Person;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.entity.Delegator;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.party.PartyMainScreen;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;

/**
 *
 * @author siranjeev
 */

public class EditPurchaseInvoiceAction extends ScreenAction {        
    final String nameStr = "Purchase Invoice";
    final String iconPathStr = "";//"clients.png";
    final String iconPathSmallStr = "";//"clients.png";
     
    public EditPurchaseInvoiceAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.EDIT_PURCHASE_INVOICE_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }    
    public EditPurchaseInvoiceAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.EDIT_PURCHASE_INVOICE_ACTION,name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }
    


    @Override
    public void actionPerformed(ActionEvent e) {
        PurchaseInvoiceController findOrderListMain = new PurchaseInvoiceController( null, session);
        findOrderListMain.loadTwoPanelInternalFrame(PurchaseInvoiceController.module, desktopPane);
    }
    
    @Override
    public Action getAction() {
        return this;
    }    
    
    static class LoadOrderListActionFactory implements ScreenClassFactoryInterface {
        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new EditPurchaseInvoiceAction(name, session, desktopPane);
        }

               
        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
             return new EditPurchaseInvoiceAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.ORDER_LIST_ACTION, new LoadOrderListActionFactory());
    }    
    
}
