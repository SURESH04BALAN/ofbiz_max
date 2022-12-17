/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.payment.*;
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
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.entity.Uom;

/**
 *
 * @author siranjeev
 */
public class UomWeightSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, Uom> valueMap = null;

    private UomWeightSingleton() {
        valueMap = new HashMap<String, Uom>();
    }

    private static class SingletonHolder {

        public static final UomWeightSingleton INSTANCE = new UomWeightSingleton();
    }

    public static UomWeightSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<Uom> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
 
        List<Uom> list =  new ArrayList<Uom>(getInstance().valueMap.values());
        Collections.sort(list, new UomComparator());   
        return list;
    }

    final static public ListModel<Uom> getValueListModal() {
        ListAdapterListModel<Uom> modal = new ListAdapterListModel<Uom>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("uomTypeId", "WEIGHT_MEASURE");
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("Uom", whereClauseMap, "description", ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                Uom uom = UOMSingleton.getUom(val.getString("uomId"));//new Uom(val);
                getInstance().valueMap.put(uom.getuomId(), uom);
            }
        } catch (Exception ex) {
            Logger.getLogger(UomWeightSingleton.class.getName()).log(Level.SEVERE, null, ex);
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

    protected static class UomComparator implements Comparator<Uom> {

        @Override
        public int compare(Uom t, Uom t1) {
            return t.getdescription().compareTo(t1.getdescription());            
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }   
}
