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
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ProductCategory;

/**
 *
 * @author siranjeev
 */
public class ProductCategorySingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductCategory> valueMap = null;

    private ProductCategorySingleton() {
        valueMap = new HashMap<String, ProductCategory>();
    }

    private static class SingletonHolder {

        public static final ProductCategorySingleton INSTANCE = new ProductCategorySingleton();
    }

    public static ProductCategorySingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductCategory> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProductCategory>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductCategory> getValueListModal() {
        ListAdapterListModel<ProductCategory> modal = new ListAdapterListModel<ProductCategory>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductCategory", whereClauseMap, null, ProductCategorySingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                ProductCategory  prodCatalog = new ProductCategory(val);
     
                getInstance().valueMap.put(prodCatalog.getProductCategoryId(), prodCatalog);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductCategorySingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ProductCategory getProductCategory(String productCategoryId) throws Exception {
        ProductCategory geoList = null;
        if (getInstance().valueMap.containsKey(productCategoryId)) {
            geoList = getInstance().valueMap.get(productCategoryId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(productCategoryId)) {
                geoList = getInstance().valueMap.get(productCategoryId);
            } else {
                throw new Exception("unable to load productCategoryId: " + productCategoryId);
            }
        }

        return geoList;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        ProductCategorySingleton.singletonSession = singletonSession;
    }
}
