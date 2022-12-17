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
import org.ofbiz.ordermax.entity.DataResourceType;

/**
 *
 * @author siranjeev
 */
public class DataResourceTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, DataResourceType> valueMap = null;

    private DataResourceTypeSingleton() {
        valueMap = new HashMap<String, DataResourceType>();
    }

    private static class SingletonHolder {

        public static final DataResourceTypeSingleton INSTANCE = new DataResourceTypeSingleton();
    }

    public static DataResourceTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<DataResourceType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<DataResourceType>(getInstance().valueMap.values());
    }

    final static public ListModel<DataResourceType> getValueListModal() {
        ListAdapterListModel<DataResourceType> modal = new ListAdapterListModel<DataResourceType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("DataResourceType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                DataResourceType  product = new DataResourceType(val);     
                getInstance().valueMap.put(product.getdataResourceTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(DataResourceTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadDataResourceType(String dataResourceTypeId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("dataResourceTypeId", dataResourceTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("DataResourceType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                DataResourceType  product = new DataResourceType(val);
     
                getInstance().valueMap.put(product.getdataResourceTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(DataResourceTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static DataResourceType getDataResourceType(String dataResourceTypeId) throws Exception {
        DataResourceType geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(dataResourceTypeId)) {
            geoList = getInstance().valueMap.get(dataResourceTypeId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadDataResourceType(dataResourceTypeId);
         
            if (getInstance().valueMap.containsKey(dataResourceTypeId)) {
                geoList = getInstance().valueMap.get(dataResourceTypeId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load dataResourceTypeId : " + dataResourceTypeId);
            }
        }

        return geoList;
    }
    
    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        DataResourceTypeSingleton.singletonSession = singletonSession;
    }
}
