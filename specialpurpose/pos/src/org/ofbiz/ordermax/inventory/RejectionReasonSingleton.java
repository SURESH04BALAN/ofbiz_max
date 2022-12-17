/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.inventory;

import org.ofbiz.ordermax.orderbase.*;
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
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.RejectionReason;

/**
 *
 * @author siranjeev
 */
public class RejectionReasonSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, RejectionReason> valueMap = null;

    private RejectionReasonSingleton() {
        valueMap = new HashMap<String, RejectionReason>();
    }

    private static class SingletonHolder {

        public static final RejectionReasonSingleton INSTANCE = new RejectionReasonSingleton();
    }

    public static RejectionReasonSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<RejectionReason> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        return new ArrayList<RejectionReason>(getInstance().valueMap.values());
    }

    final static public ListModel<RejectionReason> getValueListModal() {
        ListAdapterListModel<RejectionReason> modal = new ListAdapterListModel<RejectionReason>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("RejectionReason", ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                RejectionReason paymentType = new RejectionReason(val);
                getInstance().valueMap.put(paymentType.getrejectionId(), paymentType);
            }
        } catch (Exception ex) {
            Logger.getLogger(RejectionReasonSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static RejectionReason getRejectionReason(String rejectionId) throws Exception {
        RejectionReason paymentType = null;
        if (getInstance().valueMap.containsKey(rejectionId)) {
            paymentType = getInstance().valueMap.get(rejectionId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(rejectionId)) {
                paymentType = getInstance().valueMap.get(rejectionId);
            } else {
                throw new Exception("unable to load rejectionId : " + rejectionId);
            }
        }

        return paymentType;
    }
    public static RejectionReason getRejectionReasonDesc(String desc) throws Exception {
        List<RejectionReason> valList = getValueList();
        for (RejectionReason reason : valList) {
            if (reason.getdescription().equals(desc)) {
                return reason;
            }
        }

        throw new Exception("unable to find return reason : " + desc);
    }
    
    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        RejectionReasonSingleton.singletonSesion = singletonSesion;
    }
}
