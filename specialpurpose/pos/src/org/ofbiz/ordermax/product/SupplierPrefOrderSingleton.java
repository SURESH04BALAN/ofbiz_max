/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.party.*;
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
import org.ofbiz.ordermax.entity.SupplierPrefOrder;

/**
 *
 * @author siranjeev
 */
public class SupplierPrefOrderSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, SupplierPrefOrder> valueMap = null;

    private SupplierPrefOrderSingleton() {
        valueMap = new HashMap<String, SupplierPrefOrder>();
    }

    private static class SingletonHolder {

        public static final SupplierPrefOrderSingleton INSTANCE = new SupplierPrefOrderSingleton();
    }

    public static SupplierPrefOrderSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<SupplierPrefOrder> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<SupplierPrefOrder>(getInstance().valueMap.values());
    }

    final static public ListModel<SupplierPrefOrder> getValueListModal() {
        ListAdapterListModel<SupplierPrefOrder> modal = new ListAdapterListModel<SupplierPrefOrder>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("SupplierPrefOrder", whereClauseMap, null, SupplierPrefOrderSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                SupplierPrefOrder  supplierPrefOrder = new SupplierPrefOrder(val);
     
                getInstance().valueMap.put(supplierPrefOrder.getsupplierPrefOrderId(), supplierPrefOrder);
            }
        } catch (Exception ex) {
            Logger.getLogger(SupplierPrefOrderSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static SupplierPrefOrder geSupplierPrefOrder(String productTypeId) throws Exception {
        SupplierPrefOrder geoList = null;
        if (getInstance().valueMap.containsKey(productTypeId)) {
            geoList = getInstance().valueMap.get(productTypeId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(productTypeId)) {
                geoList = getInstance().valueMap.get(productTypeId);
            } else {
                throw new Exception("unable to load geo : " + productTypeId);
            }
        }

        return geoList;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        SupplierPrefOrderSingleton.singletonSession = singletonSession;
    }
}
