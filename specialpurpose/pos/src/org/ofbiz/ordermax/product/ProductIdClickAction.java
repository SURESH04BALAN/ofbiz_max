/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;

/**
 *
 * @author siranjeev
 */
public class ProductIdClickAction implements ActionListener {

    private GetProductIdInterface prodInterface;
    private XuiSession session;
    ControllerOptions controllerOptions = null;
    javax.swing.JDesktopPane desktopPane;

    public ProductIdClickAction(GetProductIdInterface prodInterface, ControllerOptions controllerOptions, javax.swing.JDesktopPane desktopPane, XuiSession session) {
        this.prodInterface = prodInterface;
  
        this.session = session;
        this.desktopPane = desktopPane;
        this.controllerOptions = controllerOptions;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String productId = prodInterface.getProductId();
        if (productId != null) {
            ControllerOptions options = new ControllerOptions(controllerOptions);
            controllerOptions.put("productId", productId);
            ProductMaintainController productMaintainController = new ProductMaintainController(productId, options,  session);
            productMaintainController.loadTwoPanelInternalFrame(ProductMaintainController.module, desktopPane);

        }
    }
}
