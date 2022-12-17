/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.productstore;

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
import org.ofbiz.ordermax.entity.ProductStoreGroupType;

/**
 *
 * @author siranjeev
 */
public class ProductStoreGroupTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductStoreGroupType> valueMap = null;

    private ProductStoreGroupTypeSingleton() {
        valueMap = new HashMap<String, ProductStoreGroupType>();
    }

    private static class SingletonHolder {

        public static final ProductStoreGroupTypeSingleton INSTANCE = new ProductStoreGroupTypeSingleton();
    }

    public static ProductStoreGroupTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductStoreGroupType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProductStoreGroupType>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductStoreGroupType> getValueListModal() {
        ListAdapterListModel<ProductStoreGroupType> modal = new ListAdapterListModel<ProductStoreGroupType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductStoreGroupType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductStoreGroupType  productStoreGroupType = new ProductStoreGroupType(val);     
                getInstance().valueMap.put(productStoreGroupType.getProductStoreGroupTypeId(), productStoreGroupType);
                Debug.logInfo("Find productId: "+ productStoreGroupType.getProductStoreGroupTypeId() + " getTerminalName " + productStoreGroupType.getDescription(), "getTerminalName");
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductStoreGroupTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadProductStoreGroupType(String productStoreGroupTypeId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("productStoreGroupTypeId", productStoreGroupTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductStoreGroupType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductStoreGroupType  product = new ProductStoreGroupType(val);     
                getInstance().valueMap.put(product.getProductStoreGroupTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductStoreGroupTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ProductStoreGroupType getProductStoreGroupType(String productStoreGroupTypeId) throws Exception {
        ProductStoreGroupType geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(productStoreGroupTypeId)) {
            geoList = getInstance().valueMap.get(productStoreGroupTypeId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadProductStoreGroupType( productStoreGroupTypeId);
         
            if (getInstance().valueMap.containsKey(productStoreGroupTypeId)) {
                geoList = getInstance().valueMap.get(productStoreGroupTypeId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load Pos Terminal Id : " + productStoreGroupTypeId);
            }
        }

        return geoList;
    }
    
}
