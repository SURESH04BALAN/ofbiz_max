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
import org.ofbiz.ordermax.entity.ProductFeatureType;

/**
 *
 * @author siranjeev
 */
public class ProductFeatureTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductFeatureType> valueMap = null;

    private ProductFeatureTypeSingleton() {
        valueMap = new HashMap<String, ProductFeatureType>();
    }

    private static class SingletonHolder {

        public static final ProductFeatureTypeSingleton INSTANCE = new ProductFeatureTypeSingleton();
    }

    public static ProductFeatureTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductFeatureType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProductFeatureType>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductFeatureType> getValueListModal() {
        ListAdapterListModel<ProductFeatureType> modal = new ListAdapterListModel<ProductFeatureType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductFeatureType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductFeatureType  valWrap = new ProductFeatureType(val);     
                getInstance().valueMap.put(valWrap.getproductFeatureTypeId(), valWrap);                
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductFeatureTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadProductFeatureType(String productFeatureTypeId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("productFeatureTypeId", productFeatureTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductFeatureType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductFeatureType  product = new ProductFeatureType(val);     
                getInstance().valueMap.put(product.getproductFeatureTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductFeatureTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ProductFeatureType getProductFeatureType(String productFeatureTypeId) throws Exception {
        ProductFeatureType geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(productFeatureTypeId)) {
            geoList = getInstance().valueMap.get(productFeatureTypeId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadProductFeatureType( productFeatureTypeId);
         
            if (getInstance().valueMap.containsKey(productFeatureTypeId)) {
                geoList = getInstance().valueMap.get(productFeatureTypeId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load product Feature Type Id : " + productFeatureTypeId);
            }
        }

        return geoList;
    }
    
}
