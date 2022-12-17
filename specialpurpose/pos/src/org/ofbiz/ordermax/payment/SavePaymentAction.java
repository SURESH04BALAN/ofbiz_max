/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.payment;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.screens.action.ActionType;
import org.ofbiz.ordermax.screens.action.ScreenAction;
import org.ofbiz.ordermax.screens.action.ScreenActionFactory;
import org.ofbiz.ordermax.screens.action.ScreenClassFactoryInterface;

/**
 *
 * @author siranjeev
 */

public class SavePaymentAction extends ScreenAction {        
    final String nameStr = "Create Payment";
    final String iconPathStr = "";//"clients.png";
    final String iconPathSmallStr = "";//"clients.png";
     
    public SavePaymentAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.CREATE_PAYMENT_ACTION, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }    
    public SavePaymentAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.CREATE_PAYMENT_ACTION,name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }
    


    @Override
    public void actionPerformed(ActionEvent e) {
        PaymentController paymentController = new PaymentController(  false, session);
        paymentController.loadTwoPanelInternalFrame(PaymentController.module, desktopPane);
    }
    
    @Override
    public Action getAction() {
        return this;
    }    
    
    static class LoadOrderListActionFactory implements ScreenClassFactoryInterface {
        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new SavePaymentAction(name, session, desktopPane);
        }
     
        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
             return new SavePaymentAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.CREATE_PAYMENT_ACTION, new LoadOrderListActionFactory());
    }    
    
}
