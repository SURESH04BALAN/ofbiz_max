/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.feature;

import org.ofbiz.ordermax.facility.*;
import org.ofbiz.ordermax.product.productstore.*;
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
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ProductFeature;

/**
 *
 * @author siranjeev
 */
public class ProductFeatureSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductFeature> valueMap = null;

    private ProductFeatureSingleton() {
        valueMap = new HashMap<String, ProductFeature>();
    }

    private static class SingletonHolder {

        public static final ProductFeatureSingleton INSTANCE = new ProductFeatureSingleton();
    }

    public static ProductFeatureSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductFeature> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProductFeature>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductFeature> getValueListModal() {
        ListAdapterListModel<ProductFeature> modal = new ListAdapterListModel<ProductFeature>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductFeature", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductFeature  valWrap = new ProductFeature(val);     
                getInstance().valueMap.put(valWrap.getproductFeatureId(), valWrap);                
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductFeatureSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadProductFeature(String productFeatureId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("productFeatureId", productFeatureId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductFeature", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductFeature  product = new ProductFeature(val);     
                getInstance().valueMap.put(product.getproductFeatureId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductFeatureSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ProductFeature getProductFeature(String ProductFeatureId) throws Exception {
        ProductFeature geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(ProductFeatureId)) {
            geoList = getInstance().valueMap.get(ProductFeatureId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadProductFeature( ProductFeatureId);
         
            if (getInstance().valueMap.containsKey(ProductFeatureId)) {
                geoList = getInstance().valueMap.get(ProductFeatureId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load product Feature Type Id : " + ProductFeatureId);
            }
        }

        return geoList;
    }
    
}
