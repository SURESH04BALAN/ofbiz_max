/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.payment;

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
import org.ofbiz.ordermax.entity.FinAccount;

/**
 *
 * @author siranjeev
 */
public class FinAccountSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, FinAccount> valueMap = null;

    private FinAccountSingleton() {
        valueMap = new HashMap<String, FinAccount>();
    }

    private static class SingletonHolder {

        public static final FinAccountSingleton INSTANCE = new FinAccountSingleton();
    }

    public static FinAccountSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<FinAccount> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        return new ArrayList<FinAccount>(getInstance().valueMap.values());
    }

    final static public ListModel<FinAccount> getValueListModal() {
        ListAdapterListModel<FinAccount> modal = new ListAdapterListModel<FinAccount>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("FinAccount", ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                FinAccount finAccount = new FinAccount(val);
                getInstance().valueMap.put(finAccount.getfinAccountId(), finAccount);
            }
        } catch (Exception ex) {
            Logger.getLogger(FinAccountSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static FinAccount getFinAccount(String finAccountId) throws Exception {
        FinAccount finAccount = null;
        if (getInstance().valueMap.containsKey(finAccountId)) {
            finAccount = getInstance().valueMap.get(finAccountId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(finAccountId)) {
                finAccount = getInstance().valueMap.get(finAccountId);
            } else {
                throw new Exception("unable to load finAccountId : " + finAccountId);
            }
        }

        return finAccount;
    }

    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        FinAccountSingleton.singletonSesion = singletonSesion;
    }
}
