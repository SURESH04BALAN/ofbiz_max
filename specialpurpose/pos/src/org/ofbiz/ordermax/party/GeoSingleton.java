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
public class GeoSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, Geo> valueMap = null;

    private GeoSingleton() {
        valueMap = new HashMap<String, Geo>();
    }

    private static class SingletonHolder {

        public static final GeoSingleton INSTANCE = new GeoSingleton();
    }

    public static GeoSingleton getInstance() {
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
            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("Geo", GeoSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                Geo geo = new Geo(val);
                getInstance().valueMap.put(geo.getgeoId(), geo);
            }
        } catch (Exception ex) {
            Logger.getLogger(GeoSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Geo getGeo(String geoId) throws Exception {
        Geo geo = null;
        if (getInstance().valueMap.containsKey(geoId)) {
            geo = getInstance().valueMap.get(geoId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(geoId)) {
                geo = getInstance().valueMap.get(geoId);
            } else {
                throw new Exception("unable to load geo : " + geoId);
            }
        }

        return geo;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        GeoSingleton.singletonSession = singletonSession;
    }
}
