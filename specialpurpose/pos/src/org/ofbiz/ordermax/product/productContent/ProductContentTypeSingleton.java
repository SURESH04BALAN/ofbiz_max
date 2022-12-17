/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.productContent;

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
import org.ofbiz.ordermax.entity.ProductContentType;

/**
 *
 * @author siranjeev
 */
public class ProductContentTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductContentType> valueMap = null;

    private ProductContentTypeSingleton() {
        valueMap = new HashMap<String, ProductContentType>();
    }

    private static class SingletonHolder {

        public static final ProductContentTypeSingleton INSTANCE = new ProductContentTypeSingleton();
    }

    public static ProductContentTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductContentType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProductContentType>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductContentType> getValueListModal() {
        ListAdapterListModel<ProductContentType> modal = new ListAdapterListModel<ProductContentType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductContentType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductContentType  product = new ProductContentType(val);     
                getInstance().valueMap.put(product.getproductContentTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductContentTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadProductContentType(String productStoreId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("productStoreId", productStoreId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductContentType", whereClauseMap, null, ProductContentTypeSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                ProductContentType  product = new ProductContentType(val);
     
                getInstance().valueMap.put(product.getproductContentTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductContentTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ProductContentType getProductContentType(String productContentTypeId) throws Exception {
        ProductContentType geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(productContentTypeId)) {
            geoList = getInstance().valueMap.get(productContentTypeId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadProductContentType( productContentTypeId);
         
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
        ProductContentTypeSingleton.singletonSession = singletonSession;
    }
}
