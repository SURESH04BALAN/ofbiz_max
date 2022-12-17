/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.inventory;

import org.ofbiz.ordermax.orderbase.*;
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
import org.ofbiz.ordermax.entity.InventoryItemType;

/**
 *
 * @author siranjeev
 */
public class InventoryItemTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, InventoryItemType> valueMap = null;

    private InventoryItemTypeSingleton() {
        valueMap = new HashMap<String, InventoryItemType>();
    }

    private static class SingletonHolder {

        public static final InventoryItemTypeSingleton INSTANCE = new InventoryItemTypeSingleton();
    }

    public static InventoryItemTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<InventoryItemType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        return new ArrayList<InventoryItemType>(getInstance().valueMap.values());
    }

    final static public ListModel<InventoryItemType> getValueListModal() {
        ListAdapterListModel<InventoryItemType> modal = new ListAdapterListModel<InventoryItemType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("InventoryItemType", InventoryItemTypeSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                InventoryItemType paymentType = new InventoryItemType(val);
                getInstance().valueMap.put(paymentType.getinventoryItemTypeId(), paymentType);
            }
        } catch (Exception ex) {
            Logger.getLogger(InventoryItemTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static InventoryItemType getInventoryItemType(String inventoryItemTypeId) throws Exception {
        InventoryItemType paymentType = null;
        if (getInstance().valueMap.containsKey(inventoryItemTypeId)) {
            paymentType = getInstance().valueMap.get(inventoryItemTypeId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(inventoryItemTypeId)) {
                paymentType = getInstance().valueMap.get(inventoryItemTypeId);
            } else {
                throw new Exception("unable to load inventoryItemTypeId : " + inventoryItemTypeId);
            }
        }

        return paymentType;
    }

    public static InventoryItemType getInventoryItemTypeFromDesc(String desc) throws Exception {
        List<InventoryItemType> valList = getValueList();
        for (InventoryItemType reason : valList) {
            if (reason.getdescription().equals(desc)) {
                return reason;
            }
        }

        throw new Exception("unable to find return reason : " + desc);
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSesion() {
        return singletonSession;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        InventoryItemTypeSingleton.singletonSession = singletonSesion;
    }
}
