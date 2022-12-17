/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.purchaseorder;

import org.ofbiz.ordermax.party.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.orderbase.OrderIdInterface;

/**
 *
 * @author siranjeev
 */
public class PurchaseOrderIdClickAction implements ActionListener {

    private OrderIdInterface orderIdInterface;
   
    private XuiSession session;
    ContainerPanelInterface.ContainerType containerType = null;

    javax.swing.JDesktopPane desktopPane;

    public PurchaseOrderIdClickAction(OrderIdInterface orderIdInterface,  javax.swing.JDesktopPane desktopPane, XuiSession session, ContainerPanelInterface.ContainerType contType) {
        this.orderIdInterface = orderIdInterface;

        this.session = session;
        this.desktopPane = desktopPane;
        containerType = contType;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String partyId = orderIdInterface.getOrderId();
        if (partyId != null) {
            ControllerOptions controllerOptions = new ControllerOptions();
            controllerOptions.addOrderType("PURCHASE_ORDER");
            controllerOptions.addRoleType("SUPPLIER");                        
            controllerOptions.addRoleTypeParent("SUPPLIER");               
            controllerOptions.put("orderId", partyId);
            PurchaseOrderController.runController(controllerOptions);


        }
    }
}
