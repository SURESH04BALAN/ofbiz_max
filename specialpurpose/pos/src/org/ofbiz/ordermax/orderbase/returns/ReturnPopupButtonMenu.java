/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns;

import org.ofbiz.ordermax.orderbase.*;
import javax.swing.JToggleButton;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.ordermax.composite.ReturnHeaderComposite;

/**
 *
 * @author BBS Auctions
 */
public abstract class ReturnPopupButtonMenu extends PopupButtonMenu {

    public ReturnHeaderComposite popuporderReturn;
    OrderReturnActionInterface orderReturnActInterface;

    public ReturnPopupButtonMenu(JToggleButton button, OrderReturnActionInterface orderReturnActInterface, javax.swing.JDesktopPane desktopPane) {
        this.popupButton = button;
        this.orderReturnActInterface = orderReturnActInterface;
        this.desktopPane = desktopPane;
//        this.parentFrame = parentFrame;
    }


    public abstract void loadPopMenuAction(final ReturnHeaderComposite orderReturn);


    final public void loadPopMenu(final ReturnHeaderComposite orderReturn) {
        this.popuporderReturn = orderReturn;
        popupPanel.removeAll();
        decoratePopupMenuPanel(popupPanel);
        if (popuporderReturn != null
                && UtilValidate.isNotEmpty(popuporderReturn.getReturnHeader().getreturnId())) {

//            popupPanel.add(newOrderButton/*, BorderLayout.LINE_START*/);
            loadPopMenuAction(orderReturn);
            createPopupMenu(popupPanel);
            
        }
    }
}
