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
import org.ofbiz.ordermax.entity.ContentType;

/**
 *
 * @author siranjeev
 */
public class ContentTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ContentType> valueMap = null;

    private ContentTypeSingleton() {
        valueMap = new HashMap<String, ContentType>();
    }

    private static class SingletonHolder {

        public static final ContentTypeSingleton INSTANCE = new ContentTypeSingleton();
    }

    public static ContentTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ContentType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ContentType>(getInstance().valueMap.values());
    }

    final static public ListModel<ContentType> getValueListModal() {
        ListAdapterListModel<ContentType> modal = new ListAdapterListModel<ContentType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ContentType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ContentType  product = new ContentType(val);     
                getInstance().valueMap.put(product.getcontentTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ContentTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadContentType(String productStoreId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("productStoreId", productStoreId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ContentType", whereClauseMap, null,XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                ContentType  product = new ContentType(val);
     
                getInstance().valueMap.put(product.getcontentTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(ContentTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ContentType getContentType(String contentTypeId) throws Exception {
        ContentType geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(contentTypeId)) {
            geoList = getInstance().valueMap.get(contentTypeId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadContentType( contentTypeId);
         
            if (getInstance().valueMap.containsKey(contentTypeId)) {
                geoList = getInstance().valueMap.get(contentTypeId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load ContentTypeId : " + contentTypeId);
            }
        }

        return geoList;
    }
    
    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        ContentTypeSingleton.singletonSession = singletonSession;
    }
}
