/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mvc.controller;

import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.order.order.OrderChangeHelper;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Order;
import org.ofbiz.ordermax.composite.OrderItemComposite;
import org.ofbiz.ordermax.composite.ShipmentReceiptComposite;
import org.ofbiz.ordermax.entity.Shipment;
import org.ofbiz.service.GenericServiceException;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

/**
 *
 * @author siranjeev
 */
public class OrderChangeStatusToApprovedAction extends AbstractAction {

    /**
     *
     */
    private static final long serialVersionUID = 2914235274602131249L;
    private ListAdapterListModel<?> listModel;

    public OrderChangeStatusToApprovedAction(/*ListAdapterListModel<?> listModel,*/ Order order, XuiSession session) {
//        this.listModel = listModel;
        this.order = order;
        this.session = session;
    }

    @Override
    public Object[] getKeys() {
        return new Object[]{Action.NAME};
    }

    @Override
    public Object getValue(String key) {
        if (Action.NAME.equals(key)) {
            return "APPROVE ORDER";
        } else if (Action.ACCELERATOR_KEY.equals(key)) {
            return KeyStroke.getKeyStroke('C');
        }
        return super.getValue(key);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Debug.logInfo("ReceiveInventoryAlllAction::actionPerformed: " + session.getUserId(), "Module");
            Debug.logInfo("ReceiveInventoryAlllAction::actionPerformed: " + session.getDispatcher().getName(), "Module");
            Debug.logInfo("ReceiveInventoryAlllAction::actionPerformed: " + order.getOrderId(), "Module");            
            OrderChangeHelper.orderStatusChanges(
                    session.getDispatcher(),
                    session.getUserLogin(),
                    order.getOrderId(), 
                    "ORDER_APPROVED", "ITEM_CREATED", "ITEM_APPROVED", "ITEM_APPROVED");
            
//            listModel.fireListDataChanged();
        } catch (GenericServiceException ex) {
            Logger.getLogger(OrderChangeStatusToApprovedAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public XuiSession getSession() {
        return session;
    }

    public void setSession(XuiSession session) {
        this.session = session;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    XuiSession session;
    Order order;
}
