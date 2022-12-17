/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.salesorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ContainerPanelInterface;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.orderbase.OrderIdInterface;

/**
 *
 * @author siranjeev
 */
public class SalesOrderIdClickAction implements ActionListener {

    private OrderIdInterface partyInterface;

    private XuiSession session;
    ContainerPanelInterface.ContainerType containerType = null;

    javax.swing.JDesktopPane desktopPane;

    public SalesOrderIdClickAction(OrderIdInterface partyInterface,  javax.swing.JDesktopPane desktopPane, XuiSession session, ContainerPanelInterface.ContainerType contType) {
        this.partyInterface = partyInterface;
 
        this.session = session;
        this.desktopPane = desktopPane;
        containerType = contType;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String orderId = partyInterface.getOrderId();
        if (orderId != null) {
            ControllerOptions controllerOptions = new ControllerOptions();
            controllerOptions.addOrderType("SALES_ORDER");
            controllerOptions.addRoleType("CUSTOMER");    
            controllerOptions.addRoleTypeParent("CUSTOMER");            
            controllerOptions.put("orderId", orderId);
            SalesOrderController.runController(controllerOptions);            

        }
    }
}
