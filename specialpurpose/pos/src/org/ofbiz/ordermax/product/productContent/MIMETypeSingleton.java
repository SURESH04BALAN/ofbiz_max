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
import org.ofbiz.ordermax.entity.MimeType;

/**
 *
 * @author siranjeev
 */
public class MIMETypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, MimeType> valueMap = null;

    private MIMETypeSingleton() {
        valueMap = new HashMap<String, MimeType>();
    }

    private static class SingletonHolder {

        public static final MIMETypeSingleton INSTANCE = new MIMETypeSingleton();
    }

    public static MIMETypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<MimeType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<MimeType>(getInstance().valueMap.values());
    }

    final static public ListModel<MimeType> getValueListModal() {
        ListAdapterListModel<MimeType> modal = new ListAdapterListModel<MimeType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("MimeType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                MimeType  product = new MimeType(val);     
                getInstance().valueMap.put(product.getmimeTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(MIMETypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadMimeType(String mIMETypeId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("mIMETypeId", mIMETypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("MimeType", whereClauseMap, null, MIMETypeSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                MimeType  product = new MimeType(val);
     
                getInstance().valueMap.put(product.getmimeTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(MIMETypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static MimeType getMimeType(String mimeTypeId) throws Exception {
        MimeType geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(mimeTypeId)) {
            geoList = getInstance().valueMap.get(mimeTypeId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadMimeType( mimeTypeId);
         
            if (getInstance().valueMap.containsKey(mimeTypeId)) {
                geoList = getInstance().valueMap.get(mimeTypeId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load mimeTypeId : " + mimeTypeId);
            }
        }

        return geoList;
    }
    
    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        MIMETypeSingleton.singletonSession = singletonSession;
    }
}
