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
import org.ofbiz.ordermax.entity.DataCategory;

/**
 *
 * @author siranjeev
 */
public class DataCategorySingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, DataCategory> valueMap = null;

    private DataCategorySingleton() {
        valueMap = new HashMap<String, DataCategory>();
    }

    private static class SingletonHolder {

        public static final DataCategorySingleton INSTANCE = new DataCategorySingleton();
    }

    public static DataCategorySingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<DataCategory> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<DataCategory>(getInstance().valueMap.values());
    }

    final static public ListModel<DataCategory> getValueListModal() {
        ListAdapterListModel<DataCategory> modal = new ListAdapterListModel<DataCategory>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("DataCategory", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                DataCategory  product = new DataCategory(val);     
                getInstance().valueMap.put(product.getdataCategoryId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(DataCategorySingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadDataCategory(String dataCategoryId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("dataCategoryId", dataCategoryId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("DataCategory", whereClauseMap, null, DataCategorySingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                DataCategory  product = new DataCategory(val);
     
                getInstance().valueMap.put(product.getdataCategoryId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(DataCategorySingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static DataCategory getDataCategory(String dataCategoryId) throws Exception {
        DataCategory geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(dataCategoryId)) {
            geoList = getInstance().valueMap.get(dataCategoryId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadDataCategory(dataCategoryId);
         
            if (getInstance().valueMap.containsKey(dataCategoryId)) {
                geoList = getInstance().valueMap.get(dataCategoryId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load dataCategoryId : " + dataCategoryId);
            }
        }

        return geoList;
    }
    
    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        DataCategorySingleton.singletonSession = singletonSession;
    }
}
