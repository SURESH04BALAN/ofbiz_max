/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.productstore;

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
import org.ofbiz.ordermax.entity.ProductStore;

/**
 *
 * @author siranjeev
 */
public class ProductStoreSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductStore> valueMap = null;

    private ProductStoreSingleton() {
        valueMap = new HashMap<String, ProductStore>();
    }

    private static class SingletonHolder {

        public static final ProductStoreSingleton INSTANCE = new ProductStoreSingleton();
    }

    public static ProductStoreSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductStore> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProductStore>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductStore> getValueListModal() {
        ListAdapterListModel<ProductStore> modal = new ListAdapterListModel<ProductStore>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductStore", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductStore  product = new ProductStore(val);     
                getInstance().valueMap.put(product.getproductStoreId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductStoreSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadProductStore(String productStoreId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("productStoreId", productStoreId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductStore", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductStore  product = new ProductStore(val);
     
                getInstance().valueMap.put(product.getproductStoreId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductStoreSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ProductStore getProductStore(String productStoreId) throws Exception {
        ProductStore geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(productStoreId)) {
            geoList = getInstance().valueMap.get(productStoreId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadProductStore( productStoreId);
         
            if (getInstance().valueMap.containsKey(productStoreId)) {
                geoList = getInstance().valueMap.get(productStoreId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load productStoreId : " + productStoreId);
            }
        }

        return geoList;
    }
    
}
