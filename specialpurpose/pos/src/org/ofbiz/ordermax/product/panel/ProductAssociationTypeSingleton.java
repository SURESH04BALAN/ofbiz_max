/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product.panel;

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
import org.ofbiz.ordermax.entity.ProductAssocType;
import org.ofbiz.ordermax.entity.ProductContentType;

/**
 *
 * @author siranjeev
 */
public class ProductAssociationTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ProductAssocType> valueMap = null;

    private ProductAssociationTypeSingleton() {
        valueMap = new HashMap<String, ProductAssocType>();
    }

    private static class SingletonHolder {

        public static final ProductAssociationTypeSingleton INSTANCE = new ProductAssociationTypeSingleton();
    }

    public static ProductAssociationTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ProductAssocType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ProductAssocType>(getInstance().valueMap.values());
    }

    final static public ListModel<ProductAssocType> getValueListModal() {
        ListAdapterListModel<ProductAssocType> modal = new ListAdapterListModel<ProductAssocType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductAssocType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ProductAssocType  product = new ProductAssocType(val);     
                getInstance().valueMap.put(product.getproductAssocTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductAssociationTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadProductAssocType(String parentTypeId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("parentTypeId", parentTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ProductAssocType", whereClauseMap, null, ProductAssociationTypeSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                ProductAssocType  product = new ProductAssocType(val);
     
                getInstance().valueMap.put(product.getproductAssocTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductAssociationTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ProductAssocType getProductAssocType(String productAssocTypeId) throws Exception {
        ProductAssocType geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(productAssocTypeId)) {
            geoList = getInstance().valueMap.get(productAssocTypeId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadProductAssocType( productAssocTypeId);
         
            if (getInstance().valueMap.containsKey(productAssocTypeId)) {
                geoList = getInstance().valueMap.get(productAssocTypeId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load productAssocTypeId : " + productAssocTypeId);
            }
        }

        return geoList;
    }
    
    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        ProductAssociationTypeSingleton.singletonSession = singletonSession;
    }
}
