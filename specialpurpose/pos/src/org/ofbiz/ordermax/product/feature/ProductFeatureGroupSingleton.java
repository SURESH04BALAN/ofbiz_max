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
import org.ofbiz.ordermax.entity.ProductFeatureGroup;

/**
 *
 * @author siranjeev
 */
public class ProductFeatureGroupSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductFeatureGroup> valueMap = null;

    private ProductFeatureGroupSingleton() {
        valueMap = new HashMap<String, ProductFeatureGroup>();
    }

    private static class SingletonHolder {

        public static final ProductFeatureGroupSingleton INSTANCE = new ProductFeatureGroupSingleton();
    }

    public static ProductFeatureGroupSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductFeatureGroup> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProductFeatureGroup>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductFeatureGroup> getValueListModal() {
        ListAdapterListModel<ProductFeatureGroup> modal = new ListAdapterListModel<ProductFeatureGroup>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductFeatureGroup", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductFeatureGroup  valWrap = new ProductFeatureGroup(val);     
                getInstance().valueMap.put(valWrap.getproductFeatureGroupId(), valWrap);                
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductFeatureGroupSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadProductFeatureGroup(String productFeatureGroupId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("productFeatureGroupId", productFeatureGroupId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductFeatureGroup", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductFeatureGroup  product = new ProductFeatureGroup(val);     
                getInstance().valueMap.put(product.getproductFeatureGroupId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductFeatureGroupSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ProductFeatureGroup getProductFeatureGroup(String productFeatureGroupId) throws Exception {
        ProductFeatureGroup geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(productFeatureGroupId)) {
            geoList = getInstance().valueMap.get(productFeatureGroupId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadProductFeatureGroup( productFeatureGroupId);
         
            if (getInstance().valueMap.containsKey(productFeatureGroupId)) {
                geoList = getInstance().valueMap.get(productFeatureGroupId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load product Feature Type Id : " + productFeatureGroupId);
            }
        }

        return geoList;
    }
    
}
