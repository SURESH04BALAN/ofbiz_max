/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns;

import org.ofbiz.ordermax.product.catalog.*;
import org.ofbiz.ordermax.product.*;
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
import org.ofbiz.ordermax.entity.ReturnHeaderType;

/**
 *
 * @author siranjeev
 */
public class ReturnHeaderTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ReturnHeaderType> valueMap = null;

    private ReturnHeaderTypeSingleton() {
        valueMap = new HashMap<String, ReturnHeaderType>();
    }

    private static class SingletonHolder {

        public static final ReturnHeaderTypeSingleton INSTANCE = new ReturnHeaderTypeSingleton();
    }

    public static ReturnHeaderTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ReturnHeaderType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ReturnHeaderType>(getInstance().valueMap.values());
    }

    final static public ListModel<ReturnHeaderType> getValueListModal() {
        ListAdapterListModel<ReturnHeaderType> modal = new ListAdapterListModel<ReturnHeaderType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ReturnHeaderType", whereClauseMap, null, ReturnHeaderTypeSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                ReturnHeaderType  returnHeaderType = new ReturnHeaderType(val);
     
                getInstance().valueMap.put(returnHeaderType.getreturnHeaderTypeId(), returnHeaderType);
            }
        } catch (Exception ex) {
            Logger.getLogger(ReturnHeaderTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ReturnHeaderType getReturnHeaderType(String prodCatalogCategoryId) throws Exception {
        ReturnHeaderType geoList = null;
        if (getInstance().valueMap.containsKey(prodCatalogCategoryId)) {
            geoList = getInstance().valueMap.get(prodCatalogCategoryId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(prodCatalogCategoryId)) {
                geoList = getInstance().valueMap.get(prodCatalogCategoryId);
            } else {
                throw new Exception("unable to load geo : " + prodCatalogCategoryId);
            }
        }

        return geoList;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        ReturnHeaderTypeSingleton.singletonSession = singletonSession;
    }
}
