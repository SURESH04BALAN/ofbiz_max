/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.catalog.categorycontent;

import org.ofbiz.ordermax.product.productContent.*;
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
import org.ofbiz.ordermax.entity.ProductCategoryContentType;

/**
 *
 * @author siranjeev
 */
public class ProductCategoryContentTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductCategoryContentType> valueMap = null;

    private ProductCategoryContentTypeSingleton() {
        valueMap = new HashMap<String, ProductCategoryContentType>();
    }

    private static class SingletonHolder {

        public static final ProductCategoryContentTypeSingleton INSTANCE = new ProductCategoryContentTypeSingleton();
    }

    public static ProductCategoryContentTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductCategoryContentType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProductCategoryContentType>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductCategoryContentType> getValueListModal() {
        ListAdapterListModel<ProductCategoryContentType> modal = new ListAdapterListModel<ProductCategoryContentType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductCategoryContentType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductCategoryContentType  product = new ProductCategoryContentType(val);     
                getInstance().valueMap.put(product.getprodCatContentTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductCategoryContentTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadProductCategoryContentType(String productCategoryContentTypeId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("productStoreId", productStoreId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductCategoryContentType", whereClauseMap, null, ProductCategoryContentTypeSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                ProductCategoryContentType  product = new ProductCategoryContentType(val);
     
                getInstance().valueMap.put(product.getprodCatContentTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductCategoryContentTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ProductCategoryContentType getProductCategoryContentType(String productContentTypeId) throws Exception {
        ProductCategoryContentType geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(productContentTypeId)) {
            geoList = getInstance().valueMap.get(productContentTypeId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadProductCategoryContentType( productContentTypeId);
         
            if (getInstance().valueMap.containsKey(productContentTypeId)) {
                geoList = getInstance().valueMap.get(productContentTypeId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load productContentTypeId : " + productContentTypeId);
            }
        }

        return geoList;
    }
    
    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        ProductCategoryContentTypeSingleton.singletonSession = singletonSession;
    }
}
