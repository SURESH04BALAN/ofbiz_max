/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.orderbase;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.OrderType;

/**
 *
 * @author siranjeev
 */
public class GeoStateSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, OrderType> statusMap = null;
    private GeoStateSingleton() {
        statusMap = new HashMap<String, OrderType>();
    }

    private static class OrderStatusSingletonHolder {
        
        public static final GeoStateSingleton INSTANCE = new GeoStateSingleton(); 
    }

    public static GeoStateSingleton getInstance() {
        return OrderStatusSingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }
    
    public static OrderType getOrderType(String statusId) throws Exception {
        OrderType orderType = null;
        if(getInstance().statusMap.containsKey(statusId)){
            orderType = getInstance().statusMap.get(statusId);
        }
        else{
            try {
                List<GenericValue> valueList = PosProductHelper.getGenericValueLists("OrderType", ControllerOptions.getSession().getDelegator());
                for(GenericValue val : valueList){
                    orderType = new OrderType(val);
                    getInstance().statusMap.put(orderType.getorderTypeId(), orderType);
                }
            } catch (Exception ex) {
                Logger.getLogger(GeoStateSingleton.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("unable to load party: " + statusId);
            }
        }
        
        return orderType;
    }

    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        GeoStateSingleton.singletonSesion = singletonSesion;
    }    
}
