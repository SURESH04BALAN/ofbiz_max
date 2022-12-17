/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.product.tree;

import org.ofbiz.ordermax.screens.action.*;
import javax.swing.JFrame;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;

/**
 *
 * @author siranjeev
 */
public interface ProductTreeFindPanelFactoryInterface {
    ProductTreeFindInterface createFind(ControllerOptions options);
}
