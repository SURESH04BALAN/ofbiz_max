/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

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
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.PartyType;


/**
 *
 * @author siranjeev
 */
public class PartyTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, PartyType> valueMap = null;

    private PartyTypeSingleton() {
        valueMap = new HashMap<String, PartyType>();
    }

    private static class SingletonHolder {

        public static final PartyTypeSingleton INSTANCE = new PartyTypeSingleton();
    }

    public static PartyTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<PartyType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<PartyType>(getInstance().valueMap.values());
    }

    final static public ListModel<PartyType> getValueListModal() {
        ListAdapterListModel<PartyType> modal = new ListAdapterListModel<PartyType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("PartyType", whereClauseMap, null, ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                PartyType  partyType = new PartyType(val);
     
                getInstance().valueMap.put(partyType.getpartyTypeId(), partyType);
            }
        } catch (Exception ex) {
            Logger.getLogger(PartyTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static PartyType getPartyType(String partyTypeId) throws Exception {
        PartyType geoList = null;
        if (getInstance().valueMap.containsKey(partyTypeId)) {
            geoList = getInstance().valueMap.get(partyTypeId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(partyTypeId)) {
                geoList = getInstance().valueMap.get(partyTypeId);
            } else {
                throw new Exception("unable to load geo : " + partyTypeId);
            }
        }

        return geoList;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        PartyTypeSingleton.singletonSession = singletonSession;
    }
}
