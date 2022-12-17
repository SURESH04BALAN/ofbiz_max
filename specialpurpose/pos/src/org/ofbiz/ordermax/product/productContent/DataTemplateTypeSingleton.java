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
import org.ofbiz.ordermax.entity.DataTemplateType;

/**
 *
 * @author siranjeev
 */
public class DataTemplateTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, DataTemplateType> valueMap = null;

    private DataTemplateTypeSingleton() {
        valueMap = new HashMap<String, DataTemplateType>();
    }

    private static class SingletonHolder {

        public static final DataTemplateTypeSingleton INSTANCE = new DataTemplateTypeSingleton();
    }

    public static DataTemplateTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<DataTemplateType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<DataTemplateType>(getInstance().valueMap.values());
    }

    final static public ListModel<DataTemplateType> getValueListModal() {
        ListAdapterListModel<DataTemplateType> modal = new ListAdapterListModel<DataTemplateType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("DataTemplateType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                DataTemplateType  product = new DataTemplateType(val);     
                getInstance().valueMap.put(product.getdataTemplateTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(DataTemplateTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadDataTemplateType(String dataTemplateTypeId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("dataTemplateTypeId", dataTemplateTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("DataTemplateType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                DataTemplateType  product = new DataTemplateType(val);
     
                getInstance().valueMap.put(product.getdataTemplateTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(DataTemplateTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static DataTemplateType getDataTemplateType(String dataTemplateTypeId) throws Exception {
        DataTemplateType geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(dataTemplateTypeId)) {
            geoList = getInstance().valueMap.get(dataTemplateTypeId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadDataTemplateType(dataTemplateTypeId);
         
            if (getInstance().valueMap.containsKey(dataTemplateTypeId)) {
                geoList = getInstance().valueMap.get(dataTemplateTypeId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load dataTemplateTypeId : " + dataTemplateTypeId);
            }
        }

        return geoList;
    }
    
    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        DataTemplateTypeSingleton.singletonSession = singletonSession;
    }
}
