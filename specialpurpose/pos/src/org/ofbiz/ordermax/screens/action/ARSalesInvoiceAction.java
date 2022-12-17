/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.screens.action;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JDesktopPane;
import org.ofbiz.guiapp.xui.XuiSession;
/**
 *
 * @author siranjeev
 */
public class ARSalesInvoiceAction extends ScreenAction {

    final String nameStr = "A/R Sales Invoice";
    final String iconPathStr = "sales.png";
    final String iconPathSmallStr = "salesorder_small.png";
    public ARSalesInvoiceAction(XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.ARINVOICE, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }

    public ARSalesInvoiceAction(String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        super(ActionType.ARINVOICE,name, session, desktopPane);
        this.setName(nameStr);
        this.setIconPath(iconPathStr);
        this.setIconPathSmall(iconPathSmallStr);
        loadIcons();
    }
    

    
    public void actionPerformed(ActionEvent e) {
//        ARSalesInvoiceMainScreen mainScreen = new ARSalesInvoiceMainScreen(null, session);
 //       mainScreen.loadInternalFrameScreen(PurchaseOrderMainScreen.module, desktopPane, frame);

    }

    public Action getAction() {
        return this;
    }
    
    static class ARSalesInvoiceFactory implements ScreenClassFactoryInterface {
        @Override
        public ScreenAction createAction(String name, XuiSession session, JDesktopPane desktopPane) {
            return new ARSalesInvoiceAction(name, session, desktopPane);
        }
  

        @Override
        public ScreenAction createAction(XuiSession session, JDesktopPane desktopPane) {
            return new ARSalesInvoiceAction(session, desktopPane);
        }
    }

    static {
        ScreenActionFactory.registerAction(ActionType.ARINVOICE, new ARSalesInvoiceFactory());
    }    
}
