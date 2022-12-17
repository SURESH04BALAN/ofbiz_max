/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.shoppinglist;

import org.ofbiz.ordermax.orderbase.returns.*;
import org.ofbiz.ordermax.product.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;

/**
 *
 * @author siranjeev
 */
public class SupplierShoppingCartIdClickAction implements ActionListener {

    private OrderReturnIdInterface prodInterface;
    private JFrame pFrame;
    private XuiSession session;

    javax.swing.JDesktopPane desktopPane;

    public SupplierShoppingCartIdClickAction(OrderReturnIdInterface prodInterface, JFrame pFrame, javax.swing.JDesktopPane desktopPane, XuiSession session) {
        this.prodInterface = prodInterface;
        this.pFrame = pFrame;
        this.session = session;
        this.desktopPane = desktopPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String productId = prodInterface.getBillingAccountId();
        if (productId != null) {            
            ControllerOptions options = new ControllerOptions();
            options.addReturnHeaderType("VENDOR_RETURN");
            options.addRoleType("SUPPLIER");         
            MaintainOrderReturnController.runController(options);                    
            //MaintainOrderReturnController productMaintainController = new MaintainOrderReturnController(productId, options,  session);
            //productMaintainController.loadTwoPanelInternalFrame(ProductMaintainController.module, desktopPane);
        }
    }
}
