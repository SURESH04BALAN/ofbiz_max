/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.payment.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListModel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.entity.Uom;

/**
 *
 * @author siranjeev
 */
public class UomQuantitySingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, Uom> valueMap = null;

    private UomQuantitySingleton() {
        valueMap = new TreeMap<String, Uom>();
    }

    private static class SingletonHolder {

        public static final UomQuantitySingleton INSTANCE = new UomQuantitySingleton();
    }

    public static UomQuantitySingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<Uom> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        return new ArrayList<Uom>(getInstance().valueMap.values());
    }

    final static public ListModel<Uom> getValueListModal() {
        ListAdapterListModel<Uom> modal = new ListAdapterListModel<Uom>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();

            for (Uom val : UOMSingleton.getValueList()) {
                if ("AREA_MEASURE".equals(val.getuomTypeId())
                        || "DATASPD_MEASURE".equals(val.getuomTypeId())
                        || "DATA_MEASURE".equals(val.getuomTypeId())
                        || "ENERGY_MEASURE".equals(val.getuomTypeId())
                        || "LENGTH_MEASURE".equals(val.getuomTypeId())
                        || "VOLUME_DRY_MEASURE".equals(val.getuomTypeId())
                        || "VOLUME_LIQ_MEASURE".equals(val.getuomTypeId())
                        || "OTHER_MEASURE".equals(val.getuomTypeId())
                        || "WEIGHT_MEASURE".equals(val.getuomTypeId())) {

                    getInstance().valueMap.put(val.getuomId(), val);
                }
            }
            getInstance().valueMap = sortByComparator(getInstance().valueMap);
        } catch (Exception ex) {
            Logger.getLogger(UomQuantitySingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Uom getUom(String uomId) throws Exception {
        Uom geo = null;
        if (getInstance().valueMap.containsKey(uomId)) {
            geo = getInstance().valueMap.get(uomId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(uomId)) {
                geo = getInstance().valueMap.get(uomId);
            } else {
                throw new Exception("unable to load geo : " + uomId);
            }
        }

        return geo;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        UomQuantitySingleton.singletonSession = singletonSession;
    }

    private static final Comparator<Map.Entry<String, Uom>> valueComparator = new Comparator<Map.Entry<String, Uom>>() {
        @Override
        public int compare(Map.Entry<String, Uom> e1,
                Map.Entry<String, Uom> e2) {
		//comparing by values, if it's equals, compare by keys
            // in other case, entries with equals values will be removed
            if (e1.getValue().getdescription().equals(e2.getValue().getdescription())) {
                return e1.getKey().compareTo(e2.getKey());
            }

            return (e1.getValue().getdescription()).compareTo(e2.getValue().getdescription());
        }
    };

    private static Map<String, Uom> sortByComparator(
            Map<String, Uom> unsortMap) {

        // sorted set based on comparator
        SortedSet<Map.Entry<String, Uom>> set = new TreeSet<Map.Entry<String, Uom>>(valueComparator);
        set.addAll(unsortMap.entrySet());

        // LinkedHashMap make sure order in which keys were inserted
        Map<String, Uom> sortedMap = new LinkedHashMap<String, Uom>();
        for (Iterator<Map.Entry<String, Uom>> it = set.iterator(); it
                .hasNext();) {
            Map.Entry<String, Uom> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }
}
