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
import org.ofbiz.ordermax.entity.ProductPriceType;
import org.ofbiz.ordermax.entity.ProductPriceType;

/**
 *
 * @author siranjeev
 */
public class ProductPriceTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductPriceType> valueMap = null;

    private ProductPriceTypeSingleton() {
        valueMap = new HashMap<String, ProductPriceType>();
    }

    private static class SingletonHolder {

        public static final ProductPriceTypeSingleton INSTANCE = new ProductPriceTypeSingleton();
    }

    public static ProductPriceTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductPriceType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProductPriceType>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductPriceType> getValueListModal() {
        ListAdapterListModel<ProductPriceType> modal = new ListAdapterListModel<ProductPriceType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductPriceType", whereClauseMap, null, ProductPriceTypeSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                ProductPriceType  productPriceType = new ProductPriceType(val);
     
                getInstance().valueMap.put(productPriceType.getproductPriceTypeId(), productPriceType);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductPriceTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ProductPriceType getProductPriceType(String productTypeId) throws Exception {
        ProductPriceType geoList = null;
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
        ProductPriceTypeSingleton.singletonSession = singletonSession;
    }
}
