/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment;

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
import org.ofbiz.ordermax.entity.RoleType;

/**
 *
 * @author siranjeev
 */
public class RoleTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, RoleType> valueMap = null;

    private RoleTypeSingleton() {
        valueMap = new HashMap<String, RoleType>();
    }

    private static class SingletonHolder {

        public static final RoleTypeSingleton INSTANCE = new RoleTypeSingleton();
    }

    public static RoleTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    final static public List<RoleType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }

        List<RoleType> list = new ArrayList<RoleType>(getInstance().valueMap.values());
        Collections.sort(list, new RoleTypeComparator());
        return list;        
    }

    final static public List<RoleType> getValueList(String parentTypeId) {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        List<RoleType> list = new ArrayList<RoleType>();

        if (parentTypeId != null) {
            for (Map.Entry<String, RoleType> entry : getInstance().valueMap.entrySet()) {
                if (entry.getValue().getparentTypeId()!=null
                        && entry.getValue().getparentTypeId().equals(parentTypeId) || entry.getValue().getroleTypeId().equals(parentTypeId)) {
                    list.add(entry.getValue());
                }
            }
        } else {
            list = getValueList();
        }
//          List<RoleType> newlist = new ArrayList<RoleType>(getInstance().valueMap.values());
        Collections.sort(list, new RoleTypeComparator());
        return list;  
    }

    final static public ListModel<RoleType> getValueListModal() {
        ListAdapterListModel<RoleType> modal = new ListAdapterListModel<RoleType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    final static public ListModel<RoleType> getValueListModal(String parentTypeId) {
        ListAdapterListModel<RoleType> modal = new ListAdapterListModel<RoleType>();
        modal.addAll(getInstance().getValueList(parentTypeId));
        return modal;
    }

    protected Object readResolve() {
        return getInstance();
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();

            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("RoleType", ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                RoleType roleType = new RoleType(val);
                getInstance().valueMap.put(roleType.getroleTypeId(), roleType);
            }
        } catch (Exception ex) {
            Logger.getLogger(PaymentTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static RoleType getRoleType(String roleTypeId) throws Exception {
        RoleType paymentType = null;
        if (getInstance().valueMap.containsKey(roleTypeId)) {
            paymentType = getInstance().valueMap.get(roleTypeId);
        } else {

            loadAll();
            if (getInstance().valueMap.containsKey(roleTypeId)) {
                paymentType = getInstance().valueMap.get(roleTypeId);
            } else {
                throw new Exception("unable to load roleType : " + roleTypeId);
            }
        }

        return paymentType;
    }

    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        RoleTypeSingleton.singletonSesion = singletonSesion;
    }
    
    protected static class RoleTypeComparator implements Comparator< RoleType> {
        @Override
        public int compare(RoleType t, RoleType t1) {
            return t.getdescription().compareTo(t1.getdescription());
        }
    }    
}
