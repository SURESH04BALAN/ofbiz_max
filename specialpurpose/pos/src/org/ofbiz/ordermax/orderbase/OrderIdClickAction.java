/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import static org.ofbiz.guiapp.xui.XuiSession.module;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.orderbase.purchaseorder.PurchaseOrderController;
import org.ofbiz.ordermax.orderbase.salesorder.SalesOrderController;

/**
 *
 * @author siranjeev
 */
public class OrderIdClickAction implements ActionListener {

    private OrderIdInterface orderInterface;
    private JFrame pFrame;
    private XuiSession session;

    javax.swing.JDesktopPane desktopPane;

    public OrderIdClickAction(OrderIdInterface orderInterface,  javax.swing.JDesktopPane desktopPane, XuiSession session) {
        this.orderInterface = orderInterface;

        this.session = session;
        this.desktopPane = desktopPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String orderId = orderInterface.getOrderId();
        if (orderId != null) {
            try {
                GenericValue orderValue = PosProductHelper.getGenericValueByKey("OrderHeader", UtilMisc.toMap("orderId", orderId), session.getDelegator());
                if (orderValue != null) {
                    if (Order.ORDERTYPE_PURCHSEORDER.equals(orderValue.getString("orderTypeId"))) {
                        ControllerOptions controllerOptions = new ControllerOptions();
                        controllerOptions.addOrderType("PURCHASE_ORDER");
                        controllerOptions.addRoleType("SUPPLIER");                        
                        controllerOptions.put("orderId", orderId);    
                        controllerOptions.addRoleTypeParent("SUPPLIER");                        
                        PurchaseOrderController.runController(controllerOptions);
                    } else {
                        ControllerOptions cOptions = new ControllerOptions();
                        cOptions.put("orderId", orderId);
                        cOptions.addOrderType("SALES_ORDER");
                        cOptions.addRoleType("CUSTOMER");      
                        cOptions.addRoleTypeParent("CUSTOMER");                        
                        SalesOrderController.runController(cOptions);// salesOrderController = new SalesOrderController(orderId, cOptions,  session);
                        //salesOrderController.loadSinglePanelInternalFrame(salesOrderController.module, desktopPane);
                    }
                }
            } catch (Exception ex) {
                Debug.logError(ex, module);
            }

        }
    }
}
