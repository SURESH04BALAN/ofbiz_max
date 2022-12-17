/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListModel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ShipmentBoxType;

/**
 *
 * @author siranjeev
 */
public class ShipmentBoxTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ShipmentBoxType> valueMap = null;

    private ShipmentBoxTypeSingleton() {
        valueMap = new HashMap<String, ShipmentBoxType>();
    }

    private static class SingletonHolder {

        public static final ShipmentBoxTypeSingleton INSTANCE = new ShipmentBoxTypeSingleton();
    }

    public static ShipmentBoxTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ShipmentBoxType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }

        return new ArrayList<ShipmentBoxType>(getInstance().valueMap.values());
    }

    public static ShipmentBoxType getShipmentBoxType(String shipmentBoxTypeId) throws Exception {
        ShipmentBoxType geoList = null;
        if (getInstance().valueMap.containsKey(shipmentBoxTypeId)) {
            geoList = getInstance().valueMap.get(shipmentBoxTypeId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(shipmentBoxTypeId)) {
                geoList = getInstance().valueMap.get(shipmentBoxTypeId);
            } else {
                throw new Exception("unable to load shipmentBoxTypeId : " + shipmentBoxTypeId);
            }
        }

        return geoList;
    }

    final static public ListModel<ShipmentBoxType> getValueListModal() {
        ListAdapterListModel<ShipmentBoxType> modal = new ListAdapterListModel<ShipmentBoxType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ShipmentBoxType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ShipmentBoxType shipmentBoxType = new ShipmentBoxType(val);
                getInstance().valueMap.put(shipmentBoxType.getshipmentBoxTypeId(), shipmentBoxType);
            }
        } catch (Exception ex) {
            Logger.getLogger(PartyTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        ShipmentBoxTypeSingleton.singletonSession = singletonSession;
    }
}
