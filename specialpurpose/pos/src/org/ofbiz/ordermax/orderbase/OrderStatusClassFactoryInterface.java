/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.orderbase;

import org.ofbiz.ordermax.screens.action.*;
import javax.swing.JFrame;
import org.ofbiz.guiapp.xui.XuiSession;

/**
 *
 * @author siranjeev
 */
public interface OrderStatusClassFactoryInterface {
    public ScreenAction createAction(javax.swing.JDesktopPane desktopPane,  OrderActionInterface orderActInterface);        
}
