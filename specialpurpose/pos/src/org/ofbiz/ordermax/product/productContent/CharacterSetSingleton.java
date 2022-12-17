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
import org.ofbiz.ordermax.entity.CharacterSet;

/**
 *
 * @author siranjeev
 */
public class CharacterSetSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, CharacterSet> valueMap = null;

    private CharacterSetSingleton() {
        valueMap = new HashMap<String, CharacterSet>();
    }

    private static class SingletonHolder {

        public static final CharacterSetSingleton INSTANCE = new CharacterSetSingleton();
    }

    public static CharacterSetSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<CharacterSet> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<CharacterSet>(getInstance().valueMap.values());
    }

    final static public ListModel<CharacterSet> getValueListModal() {
        ListAdapterListModel<CharacterSet> modal = new ListAdapterListModel<CharacterSet>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("CharacterSet", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                CharacterSet  product = new CharacterSet(val);     
                getInstance().valueMap.put(product.getcharacterSetId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(CharacterSetSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static protected void loadCharacterSet(String CharacterSetId){
        try {
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("characterSetId", CharacterSetId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("CharacterSet", whereClauseMap, null, CharacterSetSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                CharacterSet  product = new CharacterSet(val);
     
                getInstance().valueMap.put(product.getcharacterSetId(), product);
            }
        } catch (Exception ex) {
            Logger.getLogger(CharacterSetSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static CharacterSet getCharacterSet(String characterSetId) throws Exception {
        CharacterSet geoList = null;
//        Debug.logInfo("Find productId: "+ productId, productId);
        
        if (getInstance().valueMap.containsKey(characterSetId)) {
            geoList = getInstance().valueMap.get(characterSetId);
  //          Debug.logInfo("Found productId: "+ productId, productId);            
        } else {
//            Debug.logInfo("Not Found productId: "+ productId + " list size: " + getInstance().valueMap.size(), productId);                        
            loadCharacterSet( characterSetId);
         
            if (getInstance().valueMap.containsKey(characterSetId)) {
                geoList = getInstance().valueMap.get(characterSetId);
//            Debug.logInfo("Found AGAIN productId: "+ productId, productId);                                        
            } else {
                throw new Exception("unable to load characterSetId : " + characterSetId);
            }
        }

        return geoList;
    }
    
    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        CharacterSetSingleton.singletonSession = singletonSession;
    }
}
