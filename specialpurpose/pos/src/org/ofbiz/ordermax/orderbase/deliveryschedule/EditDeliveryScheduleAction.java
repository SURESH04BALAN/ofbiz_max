/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.orderbase.deliveryschedule;

import org.ofbiz.ordermax.orderbase.*;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.party.PartyMainScreen;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;

/**
 *
 * @author siranjeev
 */

public class EditDeliveryScheduleAction extends ScreenAction {        
    final String nameStr = "Sales Order List";
    final String iconPathStr = "";//"clients.png";
    final String iconPathSmallStr = "";//"clients.png";
     
    public EditDeliveryScheduleAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.ORDER_LIST_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }    
    public EditDeliveryScheduleAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.ORDER_LIST_ACTION,name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
//        FindOrderListController findOrderListController = new FindOrderListController( null, true, session);
//        findOrderListController.loadTwoPanelInternalFrame(PartyMainScreen.module, desktopPane, frame);
        ControllerOptions controllerOptions = new ControllerOptions();
        controllerOptions.addOrderType("SALES_ORDER");        
        EditDeliveryScheduleController findOrderListController = new EditDeliveryScheduleController( controllerOptions, session);
        findOrderListController.loadTwoPanelInternalFrame(PartyMainScreen.module, desktopPane);

    }
    
    @Override
    public Action getAction() {
        return this;
    }    
    
    static class LoadOrderListActionFactory implements ScreenClassFactoryInterface {
        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new EditDeliveryScheduleAction(name, session, desktopPane);
        }

               
        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
             return new EditDeliveryScheduleAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.ORDER_LIST_ACTION, new LoadOrderListActionFactory());
    }    
    
}
