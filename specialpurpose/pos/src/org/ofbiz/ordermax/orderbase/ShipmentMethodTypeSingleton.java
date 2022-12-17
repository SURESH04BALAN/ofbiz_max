/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

import org.ofbiz.ordermax.payment.*;
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
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ShipmentMethodType;

/**
 *
 * @author siranjeev
 */
public class ShipmentMethodTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ShipmentMethodType> valueMap = null;

    private ShipmentMethodTypeSingleton() {
        valueMap = new HashMap<String, ShipmentMethodType>();
    }

    private static class SingletonHolder {

        public static final ShipmentMethodTypeSingleton INSTANCE = new ShipmentMethodTypeSingleton();
    }

    public static ShipmentMethodTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ShipmentMethodType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        return new ArrayList<ShipmentMethodType>(getInstance().valueMap.values());
    }

    final static public ListModel<ShipmentMethodType> getValueListModal() {
        ListAdapterListModel<ShipmentMethodType> modal = new ListAdapterListModel<ShipmentMethodType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("ShipmentMethodType", ShipmentMethodTypeSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                ShipmentMethodType paymentType = new ShipmentMethodType(val);
                getInstance().valueMap.put(paymentType.getshipmentMethodTypeId(), paymentType);
            }
        } catch (Exception ex) {
            Logger.getLogger(ShipmentMethodTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ShipmentMethodType getShipmentMethodType(String shipmentMethodType) throws Exception {
        ShipmentMethodType paymentType = null;
        if (getInstance().valueMap.containsKey(shipmentMethodType)) {
            paymentType = getInstance().valueMap.get(shipmentMethodType);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(shipmentMethodType)) {
                paymentType = getInstance().valueMap.get(shipmentMethodType);
            } else {
                throw new Exception("unable to load shipmentMethodType : " + shipmentMethodType);
            }
        }

        return paymentType;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSesion() {
        return singletonSession;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        ShipmentMethodTypeSingleton.singletonSession = singletonSesion;
    }
}
