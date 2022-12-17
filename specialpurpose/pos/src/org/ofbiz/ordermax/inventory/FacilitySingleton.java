/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.inventory;

import org.ofbiz.ordermax.product.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListModel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.Facility;
import static org.ofbiz.ordermax.product.ProductSingleton.getInstance;

/**
 *
 * @author siranjeev
 */
public class FacilitySingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, Facility> valueMap = null;

    private FacilitySingleton() {
        valueMap = new HashMap<String, Facility>();
    }

    private static class SingletonHolder {

        public static final FacilitySingleton INSTANCE = new FacilitySingleton();
    }

    public static FacilitySingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<Facility> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<Facility>(getInstance().valueMap.values());
    }

    final static public ListModel<Facility> getValueListModal() {
        ListAdapterListModel<Facility> modal = new ListAdapterListModel<Facility>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("Facility", whereClauseMap, null, ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                Facility  productPriceType = new Facility(val);
                if(productPriceType.getfacilityName()==null){
                    productPriceType.setfacilityName("null");
                }
                getInstance().valueMap.put(productPriceType.getfacilityId(), productPriceType);
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductPriceTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static Facility getFacility(String facilityId) throws Exception {
        Facility geoList = null;
        if (getInstance().valueMap.containsKey(facilityId)) {
            geoList = getInstance().valueMap.get(facilityId);
        } else {
            loadAll();
            getInstance().valueMap = sortByComparator(getInstance().valueMap);                        
            if (getInstance().valueMap.containsKey(facilityId)) {
                geoList = getInstance().valueMap.get(facilityId);
            } else {
                throw new Exception("unable to load geo : " + facilityId);
            }
        }

        return geoList;
    }
    
    private static final Comparator<Map.Entry<String, Facility>> valueComparator = new Comparator<Map.Entry<String, Facility>>() {
        @Override
        public int compare(Map.Entry<String, Facility> e1,
                Map.Entry<String, Facility> e2) {
		//comparing by values, if it's equals, compare by keys
            // in other case, entries with equals values will be removed
            if (e1.getValue().getfacilityName().equals(e2.getValue().getfacilityName())) {
                return e1.getKey().compareTo(e2.getKey());
            }

            return (e1.getValue().getfacilityName()).compareTo(e2.getValue().getfacilityName());
        }
    };

    private static Map<String, Facility> sortByComparator(
            Map<String, Facility> unsortMap) {

        // sorted set based on comparator
        SortedSet<Map.Entry<String, Facility>> set = new TreeSet<Map.Entry<String, Facility>>(valueComparator);
        set.addAll(unsortMap.entrySet());

        // LinkedHashMap make sure order in which keys were inserted
        Map<String, Facility> sortedMap = new LinkedHashMap<String, Facility>();
        for (Iterator<Map.Entry<String, Facility>> it = set.iterator(); it
                .hasNext();) {
            Map.Entry<String, Facility> entry = it.next();
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        return sortedMap;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        FacilitySingleton.singletonSession = singletonSession;
    }
}
