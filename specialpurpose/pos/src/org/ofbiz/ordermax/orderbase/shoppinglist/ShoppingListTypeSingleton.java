/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.shoppinglist;

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
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ShoppingListType;

/**
 *
 * @author siranjeev
 */
public class ShoppingListTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ShoppingListType> valueMap = null;

    private ShoppingListTypeSingleton() {
        valueMap = new HashMap<String, ShoppingListType>();
    }

    private static class SingletonHolder {

        public static final ShoppingListTypeSingleton INSTANCE = new ShoppingListTypeSingleton();
    }

    public static ShoppingListTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ShoppingListType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ShoppingListType>(getInstance().valueMap.values());
    }

    final static public ListModel<ShoppingListType> getValueListModal() {
        ListAdapterListModel<ShoppingListType> modal = new ListAdapterListModel<ShoppingListType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ShoppingListType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ShoppingListType  returnHeaderType = new ShoppingListType(val);
     
                getInstance().valueMap.put(returnHeaderType.getshoppingListTypeId(), returnHeaderType);
            }
        } catch (Exception ex) {
            Logger.getLogger(ShoppingListTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ShoppingListType getShoppingListType(String shoppingListTypeId) throws Exception {
        ShoppingListType geoList = null;
        if (getInstance().valueMap.containsKey(shoppingListTypeId)) {
            geoList = getInstance().valueMap.get(shoppingListTypeId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(shoppingListTypeId)) {
                geoList = getInstance().valueMap.get(shoppingListTypeId);
            } else {
                throw new Exception("unable to load geo : " + shoppingListTypeId);
            }
        }

        return geoList;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        ShoppingListTypeSingleton.singletonSession = singletonSession;
    }
}
