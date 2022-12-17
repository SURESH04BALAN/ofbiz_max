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
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ProductType;

/**
 *
 * @author siranjeev
 */
public class ProductTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductType> valueMap = null;

    private ProductTypeSingleton() {
        valueMap = new HashMap<String, ProductType>();
    }

    private static class SingletonHolder {

        public static final ProductTypeSingleton INSTANCE = new ProductTypeSingleton();
    }

    public static ProductTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProductType>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductType> getValueListModal() {
        ListAdapterListModel<ProductType> modal = new ListAdapterListModel<ProductType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductType  productType = new ProductType(val);
     
                getInstance().valueMap.put(productType.getproductTypeId(), productType);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ProductType getProductType(String productTypeId) throws Exception {
        ProductType geoList = null;
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
        ProductTypeSingleton.singletonSession = singletonSession;
    }
}
