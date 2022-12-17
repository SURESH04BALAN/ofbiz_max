/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.catalog;

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
import org.ofbiz.ordermax.entity.ProdCatalogCategoryType;

/**
 *
 * @author siranjeev
 */
public class ProdCatalogCategoryTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProdCatalogCategoryType> valueMap = null;

    private ProdCatalogCategoryTypeSingleton() {
        valueMap = new HashMap<String, ProdCatalogCategoryType>();
    }

    private static class SingletonHolder {

        public static final ProdCatalogCategoryTypeSingleton INSTANCE = new ProdCatalogCategoryTypeSingleton();
    }

    public static ProdCatalogCategoryTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProdCatalogCategoryType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProdCatalogCategoryType>(getInstance().valueMap.values());
    }

    final static public ListModel<ProdCatalogCategoryType> getValueListModal() {
        ListAdapterListModel<ProdCatalogCategoryType> modal = new ListAdapterListModel<ProdCatalogCategoryType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProdCatalogCategoryType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProdCatalogCategoryType  prodCatalog = new ProdCatalogCategoryType(val);
     
                getInstance().valueMap.put(prodCatalog.getprodCatalogCategoryTypeId(), prodCatalog);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProdCatalogCategoryTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ProdCatalogCategoryType getProdCatalogCategoryType(String prodCatalogCategoryTypeId) throws Exception {
        ProdCatalogCategoryType geoList = null;
        if (getInstance().valueMap.containsKey(prodCatalogCategoryTypeId)) {
            geoList = getInstance().valueMap.get(prodCatalogCategoryTypeId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(prodCatalogCategoryTypeId)) {
                geoList = getInstance().valueMap.get(prodCatalogCategoryTypeId);
            } else {
                throw new Exception("unable to load prodCatalogCategoryTypeId: " + prodCatalogCategoryTypeId);
            }
        }

        return geoList;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        ProdCatalogCategoryTypeSingleton.singletonSession = singletonSession;
    }
}
