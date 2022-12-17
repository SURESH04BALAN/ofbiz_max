/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import org.ofbiz.ordermax.payment.*;
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
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.Geo;

/**
 *
 * @author siranjeev
 */
public class GeoStateSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, Geo> valueMap = null;

    private GeoStateSingleton() {
        valueMap = new HashMap<String, Geo>();
    }

    private static class SingletonHolder {

        public static final GeoStateSingleton INSTANCE = new GeoStateSingleton();
    }

    public static GeoStateSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<Geo> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        return new ArrayList<Geo>(getInstance().valueMap.values());
    }

    final static public ListModel<Geo> getValueListModal() {
        ListAdapterListModel<Geo> modal = new ListAdapterListModel<Geo>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("geoTypeId", "STATE");
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("Geo", whereClauseMap, null, GeoStateSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                Geo geo = GeoSingleton.getGeo(val.getString("geoId"));
                getInstance().valueMap.put(geo.getgeoId(), geo);
            }
        } catch (Exception ex) {
            Logger.getLogger(GeoStateSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Geo getState(String stateGeoId) throws Exception {
        Geo geo = null;
        if (getInstance().valueMap.containsKey(stateGeoId)) {
            geo = getInstance().valueMap.get(stateGeoId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(stateGeoId)) {
                geo = getInstance().valueMap.get(stateGeoId);
            } else {
                throw new Exception("unable to load geo : " + stateGeoId);
            }
        }

        return geo;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSesion() {
        return singletonSession;
    }

    public static void setSingletonSesion(XuiSession singletonSession) {
        GeoStateSingleton.singletonSession = singletonSession;
    }
}
