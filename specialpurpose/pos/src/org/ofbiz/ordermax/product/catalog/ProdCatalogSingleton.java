/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.catalog;

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
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ProdCatalog;

/**
 *
 * @author siranjeev
 */
public class ProdCatalogSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProdCatalog> valueMap = null;

    private ProdCatalogSingleton() {
        valueMap = new HashMap<String, ProdCatalog>();
    }

    private static class SingletonHolder {

        public static final ProdCatalogSingleton INSTANCE = new ProdCatalogSingleton();
    }

    public static ProdCatalogSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProdCatalog> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProdCatalog>(getInstance().valueMap.values());
    }

    final static public ListModel<ProdCatalog> getValueListModal() {
        ListAdapterListModel<ProdCatalog> modal = new ListAdapterListModel<ProdCatalog>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProdCatalog", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProdCatalog  prodCatalog = new ProdCatalog(val);
     
                getInstance().valueMap.put(prodCatalog.getprodCatalogId(), prodCatalog);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProdCatalogSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ProdCatalog getProdCatalog(String prodCatalogId) throws Exception {
        ProdCatalog geoList = null;
        if (getInstance().valueMap.containsKey(prodCatalogId)) {
            geoList = getInstance().valueMap.get(prodCatalogId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(prodCatalogId)) {
                geoList = getInstance().valueMap.get(prodCatalogId);
            } else {
                throw new Exception("unable to load prodCatalogId : " + prodCatalogId);
            }
        }

        return geoList;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        ProdCatalogSingleton.singletonSession = singletonSession;
    }
}
