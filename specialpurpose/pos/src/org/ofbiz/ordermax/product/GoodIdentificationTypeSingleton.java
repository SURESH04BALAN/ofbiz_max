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
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.GoodIdentificationType;

/**
 *
 * @author siranjeev
 */
public class GoodIdentificationTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, GoodIdentificationType> valueMap = null;

    private GoodIdentificationTypeSingleton() {
        valueMap = new HashMap<String, GoodIdentificationType>();
    }

    private static class SingletonHolder {

        public static final GoodIdentificationTypeSingleton INSTANCE = new GoodIdentificationTypeSingleton();
    }

    public static GoodIdentificationTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<GoodIdentificationType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<GoodIdentificationType>(getInstance().valueMap.values());
    }

    final static public ListModel<GoodIdentificationType> getValueListModal() {
        ListAdapterListModel<GoodIdentificationType> modal = new ListAdapterListModel<GoodIdentificationType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("GoodIdentificationType", whereClauseMap, null, GoodIdentificationTypeSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                GoodIdentificationType  goodIdentificationType = new GoodIdentificationType(val);
     
                getInstance().valueMap.put(goodIdentificationType.getgoodIdentificationTypeId(), goodIdentificationType);
            }
        } catch (Exception ex) {
            Logger.getLogger(GoodIdentificationTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static GoodIdentificationType getGoodIdentificationType(String goodIdentificationTypeId) throws Exception {
        GoodIdentificationType goodIdentificationType = null;
        if (getInstance().valueMap.containsKey(goodIdentificationTypeId)) {
            goodIdentificationType = getInstance().valueMap.get(goodIdentificationTypeId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(goodIdentificationTypeId)) {
                goodIdentificationType = getInstance().valueMap.get(goodIdentificationTypeId);
            } else {
                throw new Exception("unable to load geo : " + goodIdentificationTypeId);
            }
        }

        return goodIdentificationType;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        GoodIdentificationTypeSingleton.singletonSession = singletonSession;
    }
}
