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
import org.ofbiz.ordermax.entity.ReturnStatus;

/**
 *
 * @author siranjeev
 */
public class ReturnStatusSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ReturnStatus> valueMap = null;

    private ReturnStatusSingleton() {
        valueMap = new HashMap<String, ReturnStatus>();
    }

    private static class SingletonHolder {

        public static final ReturnStatusSingleton INSTANCE = new ReturnStatusSingleton();
    }

    public static ReturnStatusSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ReturnStatus> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ReturnStatus>(getInstance().valueMap.values());
    }

    final static public ListModel<ReturnStatus> getValueListModal() {
        ListAdapterListModel<ReturnStatus> modal = new ListAdapterListModel<ReturnStatus>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ReturnStatus", whereClauseMap, null, ReturnStatusSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                ReturnStatus  returnHeaderType = new ReturnStatus(val);
     
                getInstance().valueMap.put(returnHeaderType.getreturnStatusId(), returnHeaderType);
            }
        } catch (Exception ex) {
            Logger.getLogger(ReturnStatusSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ReturnStatus getReturnReason(String returnReasonId) throws Exception {
        ReturnStatus geoList = null;
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

 

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        ReturnStatusSingleton.singletonSession = singletonSession;
    }
}
