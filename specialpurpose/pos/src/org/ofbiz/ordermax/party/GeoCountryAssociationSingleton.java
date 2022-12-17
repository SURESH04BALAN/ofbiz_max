/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

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
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.Geo;

/**
 *
 * @author siranjeev
 */
public class GeoCountryAssociationSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, List<Geo>> valueMap = null;

    private GeoCountryAssociationSingleton() {
        valueMap = new HashMap<String, List<Geo>>();
    }

    private static class SingletonHolder {

        public static final GeoCountryAssociationSingleton INSTANCE = new GeoCountryAssociationSingleton();
    }

    public static GeoCountryAssociationSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<Geo> getValueList(String countryId) {
        if (getInstance().valueMap.isEmpty() || getInstance().valueMap.get(countryId) == null ) {
            loadAll(countryId);
        }
        Debug.logInfo("countryId: " + countryId, countryId);
        if (getInstance().valueMap.get(countryId) != null) {
            return new ArrayList<Geo>(getInstance().valueMap.get(countryId));
        } else {
            return new ArrayList<Geo>();
        }
    }

    final static public ListModel<Geo> getValueListModal(String countryId) {
        ListAdapterListModel<Geo> modal = new ListAdapterListModel<Geo>();
        modal.addAll(getInstance().getValueList(countryId));
        return modal;
    }

    static protected void loadAll(String countryId) {
        try {
//            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("geoId", countryId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("GeoAssoc", whereClauseMap, null, XuiContainer.getSession().getDelegator());
            for (GenericValue val : valueList) {
                Geo geo = GeoSingleton.getGeo(val.getString("geoIdTo"));

                if (getInstance().valueMap.containsKey(countryId) == false) {
                    getInstance().valueMap.put(countryId, new ArrayList<Geo>());
                }

                getInstance().valueMap.get(countryId).add(geo);
            }
        } catch (Exception ex) {
            Logger.getLogger(GeoCountryAssociationSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List<Geo> getAssociatedRegions(String countryGeoId) throws Exception {
        List<Geo> geoList = null;
        if (getInstance().valueMap.containsKey(countryGeoId)) {
            geoList = getInstance().valueMap.get(countryGeoId);
        } else {
            loadAll(countryGeoId);
            if (getInstance().valueMap.containsKey(countryGeoId)) {
                geoList = getInstance().valueMap.get(countryGeoId);
            } else {
                throw new Exception("unable to load geo : " + countryGeoId);
            }
        }

        return geoList;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        GeoCountryAssociationSingleton.singletonSession = singletonSession;
    }
}
