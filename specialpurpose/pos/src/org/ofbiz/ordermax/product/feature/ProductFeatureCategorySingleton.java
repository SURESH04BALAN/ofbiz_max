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
import org.ofbiz.ordermax.entity.ProductFeatureCategory;

/**
 *
 * @author siranjeev
 */
public class ProductFeatureCategorySingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductFeatureCategory> valueMap = null;

    private ProductFeatureCategorySingleton() {
        valueMap = new HashMap<String, ProductFeatureCategory>();
    }

    private static class SingletonHolder {

        public static final ProductFeatureCategorySingleton INSTANCE = new ProductFeatureCategorySingleton();
    }

    public static ProductFeatureCategorySingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductFeatureCategory> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProductFeatureCategory>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductFeatureCategory> getValueListModal() {
        ListAdapterListModel<ProductFeatureCategory> modal = new ListAdapterListModel<ProductFeatureCategory>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductFeatureCategory", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductFeatureCategory  valWrap = new ProductFeatureCategory(val);     
                getInstance().valueMap.put(valWrap.getproductFeatureCategoryId(), valWrap);                
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductFeatureCategorySingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadProductFeatureType(String productFeatureCategoryId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("productFeatureCategoryId", productFeatureCategoryId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductFeatureCategory", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductFeatureCategory  product = new ProductFeatureCategory(val);     
                getInstance().valueMap.put(product.getproductFeatureCategoryId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductFeatureCategorySingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ProductFeatureCategory getProductFeatureCategory(String productFeatureCategoryId) throws Exception {
        ProductFeatureCategory geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(productFeatureCategoryId)) {
            geoList = getInstance().valueMap.get(productFeatureCategoryId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadProductFeatureType( productFeatureCategoryId);
         
            if (getInstance().valueMap.containsKey(productFeatureCategoryId)) {
                geoList = getInstance().valueMap.get(productFeatureCategoryId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load product Feature Type Id : " + productFeatureCategoryId);
            }
        }

        return geoList;
    }
    
}
