/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase.returns;

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
import org.ofbiz.ordermax.entity.ReturnType;

/**
 *
 * @author siranjeev
 */
public class ReturnTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ReturnType> valueMap = null;

    private ReturnTypeSingleton() {
        valueMap = new HashMap<String, ReturnType>();
    }

    private static class SingletonHolder {

        public static final ReturnTypeSingleton INSTANCE = new ReturnTypeSingleton();
    }

    public static ReturnTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ReturnType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ReturnType>(getInstance().valueMap.values());
    }

    final static public ListModel<ReturnType> getValueListModal() {
        ListAdapterListModel<ReturnType> modal = new ListAdapterListModel<ReturnType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ReturnType", whereClauseMap, null, ReturnTypeSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                ReturnType  returnHeaderType = new ReturnType(val);
     
                getInstance().valueMap.put(returnHeaderType.getreturnTypeId(), returnHeaderType);
            }
        } catch (Exception ex) {
            Logger.getLogger(ReturnTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ReturnType getReturnReason(String returnReasonId) throws Exception {
        ReturnType geoList = null;
        if (getInstance().valueMap.containsKey(returnReasonId)) {
            geoList = getInstance().valueMap.get(returnReasonId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(returnReasonId)) {
                geoList = getInstance().valueMap.get(returnReasonId);
            } else {
                throw new Exception("unable to load return reason : " + returnReasonId);
            }
        }

        return geoList;
    }

    public static ReturnType getReturnTypeFromDesc(String reasonDsc) throws Exception {
                List<ReturnType> valList = getValueList();
        for (ReturnType reason : valList) {
            if (reason.getdescription().equals(reasonDsc)) {
                return reason;
            }
        }

        throw new Exception("unable to find return reason : " + reasonDsc);
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        ReturnTypeSingleton.singletonSession = singletonSession;
    }
}
