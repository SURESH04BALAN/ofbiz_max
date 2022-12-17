/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.screens.action;

import java.util.HashMap;
import javax.swing.JFrame;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.product.productloader.ProductTreeArraySingleton;

/**
 *
 * @author siranjeev
 */
public class ScreenActionFactory {

    private static final HashMap<ActionType, ScreenClassFactoryInterface> registeredScreenAction;

    static {
        registeredScreenAction = new HashMap<ActionType, ScreenClassFactoryInterface>();
    }

    private static class ScreenActionFactorySingletonHolder {

        public static final ScreenActionFactory INSTANCE = new ScreenActionFactory();
    }

    public static ScreenActionFactory getInstance() {
        return ScreenActionFactorySingletonHolder.INSTANCE;
    }

    static public void registerAction(ActionType actionType, ScreenClassFactoryInterface p) {
        registeredScreenAction.put(actionType, p);
    }

    static public ScreenAction createAction(ActionType actionType, String name, XuiSession session, javax.swing.JDesktopPane desktopPane) {
        return registeredScreenAction.get(actionType).createAction(name, session, desktopPane);
    }

  
    public static HashMap<ActionType, ScreenClassFactoryInterface> getRegisteredScreenAction() {
        return registeredScreenAction;
    }
}
