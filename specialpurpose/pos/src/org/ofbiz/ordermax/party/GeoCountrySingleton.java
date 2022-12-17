/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
public class GeoCountrySingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, Geo> valueMap = null;

    private GeoCountrySingleton() {
        valueMap = new HashMap<String, Geo>();
    }

    private static class SingletonHolder {

        public static final GeoCountrySingleton INSTANCE = new GeoCountrySingleton();
    }

    public static GeoCountrySingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<Geo> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        //return new ArrayList<Geo>(getInstance().valueMap.values());

        List<Geo> list = new ArrayList<Geo>(getInstance().valueMap.values());
        Collections.sort(list, new GeoComparator());
        return list;
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
            whereClauseMap.put("geoTypeId", "COUNTRY");
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("Geo", whereClauseMap, null, GeoCountrySingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                Geo geo = GeoSingleton.getGeo(val.getString("geoId"));//new Geo(val);
                getInstance().valueMap.put(geo.getgeoId(), geo);
            }
        } catch (Exception ex) {
            Logger.getLogger(GeoCountrySingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Geo getCountry(String countryGeoId) throws Exception {
        Geo geo = null;
        if (getInstance().valueMap.containsKey(countryGeoId)) {
            geo = getInstance().valueMap.get(countryGeoId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(countryGeoId)) {
                geo = getInstance().valueMap.get(countryGeoId);
            } else {
                throw new Exception("unable to load geo : " + countryGeoId);
            }
        }

        return geo;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        GeoCountrySingleton.singletonSession = singletonSession;
    }

    protected static class GeoComparator implements Comparator< Geo> {

        @Override
        public int compare(Geo t, Geo t1) {
            return t.getgeoName().compareTo(t1.getgeoName());
        }
    }
}
