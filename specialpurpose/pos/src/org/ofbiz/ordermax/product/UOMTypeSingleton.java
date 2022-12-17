/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.party.*;
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
import org.ofbiz.ordermax.entity.UomType;
import org.ofbiz.ordermax.entity.UomType;

/**
 *
 * @author siranjeev
 */
public class UOMTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, UomType> valueMap = null;

    private UOMTypeSingleton() {
        valueMap = new HashMap<String, UomType>();
    }

    private static class SingletonHolder {

        public static final UOMTypeSingleton INSTANCE = new UOMTypeSingleton();
    }

    public static UOMTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<UomType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<UomType>(getInstance().valueMap.values());
    }

    final static public ListModel<UomType> getValueListModal() {
        ListAdapterListModel<UomType> modal = new ListAdapterListModel<UomType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("UomType", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                UomType  uomType = new UomType(val);
     
                getInstance().valueMap.put(uomType.getuomTypeId(), uomType);
            }
        } catch (Exception ex) {
            Logger.getLogger(UOMTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static UomType getUomType(String uomTypeId) throws Exception {
        UomType uomList = null;
        if (getInstance().valueMap.containsKey(uomTypeId)) {
            uomList = getInstance().valueMap.get(uomTypeId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(uomTypeId)) {
                uomList = getInstance().valueMap.get(uomTypeId);
            } else {
                throw new Exception("unable to uomTypeId : " + uomTypeId);
            }
        }

        return uomList;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        UOMTypeSingleton.singletonSession = singletonSession;
    }
}
