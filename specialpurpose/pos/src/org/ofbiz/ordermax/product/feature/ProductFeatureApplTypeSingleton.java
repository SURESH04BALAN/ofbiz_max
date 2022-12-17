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
import org.ofbiz.ordermax.entity.ProductFeatureApplType;

/**
 *
 * @author siranjeev
 */
public class ProductFeatureApplTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductFeatureApplType> valueMap = null;

    private ProductFeatureApplTypeSingleton() {
        valueMap = new HashMap<String, ProductFeatureApplType>();
    }

    private static class SingletonHolder {

        public static final ProductFeatureApplTypeSingleton INSTANCE = new ProductFeatureApplTypeSingleton();
    }

    public static ProductFeatureApplTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductFeatureApplType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProductFeatureApplType>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductFeatureApplType> getValueListModal() {
        ListAdapterListModel<ProductFeatureApplType> modal = new ListAdapterListModel<ProductFeatureApplType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductFeatureApplType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductFeatureApplType  valWrap = new ProductFeatureApplType(val);     
                getInstance().valueMap.put(valWrap.getproductFeatureApplTypeId(), valWrap);                
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductFeatureApplTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadProductFeatureApplType(String productFeatureTypeId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("productFeatureApplTypeId", productFeatureTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductFeatureApplType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductFeatureApplType  product = new ProductFeatureApplType(val);     
                getInstance().valueMap.put(product.getproductFeatureApplTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductFeatureApplTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ProductFeatureApplType getProductFeatureApplType(String productFeatureTypeId) throws Exception {
        ProductFeatureApplType geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(productFeatureTypeId)) {
            geoList = getInstance().valueMap.get(productFeatureTypeId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadProductFeatureApplType( productFeatureTypeId);
         
            if (getInstance().valueMap.containsKey(productFeatureTypeId)) {
                geoList = getInstance().valueMap.get(productFeatureTypeId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load product Feature Type Id : " + productFeatureTypeId);
            }
        }

        return geoList;
    }
    
    public static ProductFeatureApplType getReturnStatusFromDesc(String desc) throws Exception {
        List<ProductFeatureApplType> valList = getValueList();
        for (ProductFeatureApplType reason : valList) {
            if (reason.getdescription().equals(desc)) {
                return reason;
            }
        }

        throw new Exception("unable to find status desc : " + desc);
    }    
}
