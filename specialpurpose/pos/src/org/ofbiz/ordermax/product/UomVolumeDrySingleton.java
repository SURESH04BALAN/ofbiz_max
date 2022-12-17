/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

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
import org.ofbiz.ordermax.entity.Uom;
import org.ofbiz.ordermax.entity.Uom;

/**
 *
 * @author siranjeev
 */
public class UomVolumeDrySingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, Uom> valueMap = null;

    private UomVolumeDrySingleton() {
        valueMap = new HashMap<String, Uom>();
    }

    private static class SingletonHolder {

        public static final UomVolumeDrySingleton INSTANCE = new UomVolumeDrySingleton();
    }

    public static UomVolumeDrySingleton getInstance() {
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
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
            whereClauseMap.put("uomTypeId", "VOLUME_DRY_MEASURE");
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("Uom", whereClauseMap, "description", UomVolumeDrySingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                Uom uom = UOMSingleton.getUom(val.getString("uomId"));//new Uom(val);
                getInstance().valueMap.put(uom.getuomId(), uom);
            }
        } catch (Exception ex) {
            Logger.getLogger(UomVolumeDrySingleton.class.getName()).log(Level.SEVERE, null, ex);
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
        UomVolumeDrySingleton.singletonSession = singletonSession;
    }
}
