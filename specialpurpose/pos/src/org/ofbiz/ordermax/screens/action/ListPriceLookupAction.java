/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens.action;

import java.awt.event.ActionEvent;
import java.util.Map;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.SupplierProductAndListPriceData;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.entity.ProductPrice;
import org.ofbiz.ordermax.generic.GenericPartySearchPanel;
import org.ofbiz.ordermax.generic.GenericValueDetailTableDialog;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;

/**
 *
 * @author siranjeev
 */
public class ListPriceLookupAction extends ScreenAction {

    final String nameStr = "Sales List Price";
    final String iconPathStr = "sales.png";
    final String iconPathSmallStr = "salesorder_small.png";

    public ListPriceLookupAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.LISTPRICE_LOOKUP_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public ListPriceLookupAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.LISTPRICE_LOOKUP_ACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }


    public ListPriceLookupAction(String name, XuiSession session) {
        super(ActionType.LISTPRICE_LOOKUP_ACTION, session);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }
    private OrderItemComposite orderItem = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        if (orderItem != null && orderItem.getProductItemPrice() != null
                && orderItem.getProductItemPrice().getListPrice() != null) {

            final GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(null, false, XuiContainer.getSession(), ProductPrice.ColumnNameId);
            dlg.setDoCellRendering(false);
            dlg.setReadOnlyTable(true);
            dlg.hideDetailPanel();
            dlg.setupOrderTableList(orderItem.getProductItemPrice().getListPrice().getAllGenericValueElement());
            dlg.setVisible(true);
        }
    }

    @Override
    public Action getAction() {
        return this;
    }

    public OrderItemComposite getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(OrderItemComposite orderItem) {
        this.orderItem = orderItem;
    }

    
    static class ListPriceLookupActionFactory implements ScreenClassFactoryInterface {

        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new ListPriceLookupAction(name, session, desktopPane);
        }


        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
            return new ListPriceLookupAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.LISTPRICE_LOOKUP_ACTION, new ListPriceLookupActionFactory());
    }
}
