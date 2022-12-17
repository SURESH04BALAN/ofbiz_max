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
import org.ofbiz.ordermax.entity.ReturnReason;

/**
 *
 * @author siranjeev
 */
public class ReturnReasonSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ReturnReason> valueMap = null;

    private ReturnReasonSingleton() {
        valueMap = new HashMap<String, ReturnReason>();
    }

    private static class SingletonHolder {

        public static final ReturnReasonSingleton INSTANCE = new ReturnReasonSingleton();
    }

    public static ReturnReasonSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ReturnReason> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ReturnReason>(getInstance().valueMap.values());
    }

    final static public ListModel<ReturnReason> getValueListModal() {
        ListAdapterListModel<ReturnReason> modal = new ListAdapterListModel<ReturnReason>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ReturnReason", whereClauseMap, null, ReturnReasonSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                ReturnReason  returnHeaderType = new ReturnReason(val);
     
                getInstance().valueMap.put(returnHeaderType.getreturnReasonId(), returnHeaderType);
            }
        } catch (Exception ex) {
            Logger.getLogger(ReturnReasonSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ReturnReason getReturnReason(String returnReasonId) throws Exception {
        ReturnReason geoList = null;
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

    public static ReturnReason getReturnReasonFromDesc(String reasonDsc) throws Exception {
                List<ReturnReason> valList = getValueList();
        for (ReturnReason reason : valList) {
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
        ReturnReasonSingleton.singletonSession = singletonSession;
    }
}
