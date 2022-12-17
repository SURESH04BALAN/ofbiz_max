/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens.action;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Map;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import org.ofbiz.entity.GenericValue;
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
public class GenericPriceLookupAction extends ScreenAction {

    final String nameStr = "Sales List Price";
    final String iconPathStr = "sales.png";
    final String iconPathSmallStr = "salesorder_small.png";

    public GenericPriceLookupAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.GENERICPRICE_LOOKUP_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public GenericPriceLookupAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.GENERICPRICE_LOOKUP_ACTION, name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }


    public GenericPriceLookupAction(String name, XuiSession session) {
        super(ActionType.GENERICPRICE_LOOKUP_ACTION, session);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }
    public GenericPriceLookupAction(String name, XuiSession session, String[][] IdColumnName) {
        super(ActionType.GENERICPRICE_LOOKUP_ACTION, session);
        this.setName(name);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        this.IdColumnName = IdColumnName;
        loadIcons();
    }
    
    private String[][] IdColumnName;
    private List<GenericValue> genericValueDataList = null;
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (IdColumnName != null && genericValueDataList != null) {

            final GenericValueDetailTableDialog dlg = new GenericValueDetailTableDialog(null, false, XuiContainer.getSession(), IdColumnName);
            dlg.setTitle(name);
            dlg.setDoCellRendering(false);
            dlg.setReadOnlyTable(true);
            dlg.hideDetailPanel();
            dlg.setupOrderTableList(genericValueDataList);
            dlg.setVisible(true);
        }
    }

    @Override
    public Action getAction() {
        return this;
    }

    public String[][] getIdColumnName() {
        return IdColumnName;
    }

    public void setIdColumnName(String[][] IdColumnName) {
        this.IdColumnName = IdColumnName;
    }

    public List<GenericValue> getGenericValueDataList() {
        return genericValueDataList;
    }

    public void setGenericValueDataList(List<GenericValue> genericValueDataList) {
        this.genericValueDataList = genericValueDataList;
    }
    @Override
    public void setActionMenuItem(JMenuItem menuItem) {
        menuItem.setAction(this); //.add(new JMenuItem(rf.getAction()));
    }
    public void loadIcons() {
        this.putValue(Action.NAME, name);
        this.putValue(Action.SHORT_DESCRIPTION, name);
    }

    static class GenericPriceLookupActionFactory implements ScreenClassFactoryInterface {

        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new GenericPriceLookupAction(name, session, desktopPane);
        }



        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
            return new GenericPriceLookupAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.GENERICPRICE_LOOKUP_ACTION, new GenericPriceLookupActionFactory());
    }
}
