/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

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
import org.ofbiz.ordermax.entity.TermType;

/**
 *
 * @author siranjeev
 */
public class TermTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, TermType> valueMap = null;

    private TermTypeSingleton() {
        valueMap = new HashMap<String, TermType>();
    }

    private static class SingletonHolder {

        public static final TermTypeSingleton INSTANCE = new TermTypeSingleton();
    }

    public static TermTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<TermType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        return new ArrayList<TermType>(getInstance().valueMap.values());
    }

    final static public ListModel<TermType> getValueListModal() {
        ListAdapterListModel<TermType> modal = new ListAdapterListModel<TermType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("TermType", ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                TermType paymentType = new TermType(val);
                getInstance().valueMap.put(paymentType.gettermTypeId(), paymentType);
            }
        } catch (Exception ex) {
            Logger.getLogger(TermTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static TermType getTermType(String termTypeId) throws Exception {
        TermType paymentType = null;
        if (getInstance().valueMap.containsKey(termTypeId)) {
            paymentType = getInstance().valueMap.get(termTypeId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(termTypeId)) {
                paymentType = getInstance().valueMap.get(termTypeId);
            } else {
                throw new Exception("unable to load termTypeId : " + termTypeId);
            }
        }

        return paymentType;
    }

    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        TermTypeSingleton.singletonSesion = singletonSesion;
    }
}
