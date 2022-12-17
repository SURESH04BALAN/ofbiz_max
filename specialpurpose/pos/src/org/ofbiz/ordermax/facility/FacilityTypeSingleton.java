/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.facility;

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
import org.ofbiz.ordermax.entity.FacilityType;

/**
 *
 * @author siranjeev
 */
public class FacilityTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, FacilityType> valueMap = null;

    private FacilityTypeSingleton() {
        valueMap = new HashMap<String, FacilityType>();
    }

    private static class SingletonHolder {

        public static final FacilityTypeSingleton INSTANCE = new FacilityTypeSingleton();
    }

    public static FacilityTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<FacilityType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<FacilityType>(getInstance().valueMap.values());
    }

    final static public ListModel<FacilityType> getValueListModal() {
        ListAdapterListModel<FacilityType> modal = new ListAdapterListModel<FacilityType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("FacilityType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                FacilityType  posTerminal = new FacilityType(val);     
                getInstance().valueMap.put(posTerminal.getfacilityTypeId(), posTerminal);
                Debug.logInfo("Find productId: "+ posTerminal.getfacilityTypeId() + " getTerminalName " + posTerminal.getdescription(), "getTerminalName");
            }
        } catch (Exception ex) {
            Logger.getLogger(FacilityTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadFacilityType(String posTerminalId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("posTerminalId", posTerminalId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("FacilityType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                FacilityType  product = new FacilityType(val);     
                getInstance().valueMap.put(product.getfacilityTypeId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(FacilityTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static FacilityType getFacilityType(String posTerminalId) throws Exception {
        FacilityType geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(posTerminalId)) {
            geoList = getInstance().valueMap.get(posTerminalId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadFacilityType( posTerminalId);
         
            if (getInstance().valueMap.containsKey(posTerminalId)) {
                geoList = getInstance().valueMap.get(posTerminalId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load Pos Terminal Id : " + posTerminalId);
            }
        }

        return geoList;
    }
    
}
