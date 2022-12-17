/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListModel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.OrderType;

/**
 *
 * @author siranjeev
 */
public class OrderTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, OrderType> valueMap = null;

    private OrderTypeSingleton() {
        valueMap = new HashMap<String, OrderType>();
    }

    private static class SingletonHolder {

        public static final OrderTypeSingleton INSTANCE = new OrderTypeSingleton();
    }

    public static OrderTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<OrderType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        return new ArrayList<OrderType>(getInstance().valueMap.values());
    }

    final static public ListModel<OrderType> getValueListModal() {
        ListAdapterListModel<OrderType> modal = new ListAdapterListModel<OrderType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("OrderType", ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                OrderType paymentType = new OrderType(val);
                getInstance().valueMap.put(paymentType.getorderTypeId(), paymentType);
            }
        } catch (Exception ex) {
            Logger.getLogger(OrderTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static OrderType getOrderType(String orderTypeId) throws Exception {
        OrderType paymentType = null;
        if (getInstance().valueMap.containsKey(orderTypeId)) {
            paymentType = getInstance().valueMap.get(orderTypeId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(orderTypeId)) {
                paymentType = getInstance().valueMap.get(orderTypeId);
            } else {
                throw new Exception("unable to load paymentType : " + orderTypeId);
            }
        }

        return paymentType;
    }

    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        OrderTypeSingleton.singletonSesion = singletonSesion;
    }
}
