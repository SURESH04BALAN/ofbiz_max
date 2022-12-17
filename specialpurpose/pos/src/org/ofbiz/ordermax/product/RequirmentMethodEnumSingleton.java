/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.product;

import org.ofbiz.ordermax.orderbase.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListModel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;

import org.ofbiz.ordermax.payment.PaymentTypeSingleton;

/**
 *
 * @author siranjeev
 */
public class RequirmentMethodEnumSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, String> valueMap = null;

    private RequirmentMethodEnumSingleton() {
        valueMap = new HashMap<String, String>();
    }

    private static class SingletonHolder {

        public static final RequirmentMethodEnumSingleton INSTANCE = new RequirmentMethodEnumSingleton();
    }

    public static RequirmentMethodEnumSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<String> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }

        return new ArrayList<String>(getInstance().valueMap.values());
    }

    final static public ListModel<String> getValueListModal() {
        ListAdapterListModel<String> modal = new ListAdapterListModel<String>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    final static public ListModel<String> getValueListModal(String typeId) {

        ListModel<String> modalRet = null;
        if (typeId != null) {
            if (getInstance().valueMap.isEmpty()) {
                loadAll();
            }

            ListAdapterListModel<String> modal = new ListAdapterListModel<String>();
            for (Map.Entry<String, String> entry : getInstance().valueMap.entrySet()) {
                if (entry.getValue().equals(typeId)) {
                    modal.add(entry.getValue());
                }
            }
            modalRet = modal;
        } else {
            modalRet = getValueListModal();
        }

        return modalRet;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Locale locale = (Locale) Locale.getDefault();
            String PRODRQM_NONE = "No Requirement Created";
            String PRODRQM_AUTO = "Automatic For Every Sales Order";
            String STOCK_QOH = "When QOH Reaches Minimum Stock for Product-Facility";
            String STOCK_ATP = "When ATP Reaches Minimum Stock for Product-Facility";
            String ATP = "Requirement for order when ATP Reaches Minimum Stock for Product-Facility";
            String DROPS = "Drop-ship only";
            String DROPS_ATP = "Auto drop-ship on low quantity";

            getInstance().valueMap.put("PRODRQM_NONE", PRODRQM_NONE);
            getInstance().valueMap.put("PRODRQM_AUTO", PRODRQM_AUTO);
            getInstance().valueMap.put("PRODRQM_STOCK", STOCK_QOH);
            getInstance().valueMap.put("PRODRQM_STOCK_ATP", STOCK_ATP);
            getInstance().valueMap.put("PRODRQM_ATP", ATP);
            getInstance().valueMap.put("PRODRQM_DS", DROPS);
            getInstance().valueMap.put("PRODRQM_DSATP", DROPS_ATP);

        } catch (Exception ex) {
            Logger.getLogger(RequirmentMethodEnumSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getString(String statusId) throws Exception {
        String string = null;
        if (getInstance().valueMap.containsKey(statusId)) {
            string = getInstance().valueMap.get(statusId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(statusId)) {
                string = getInstance().valueMap.get(statusId);
            } else {
                throw new Exception("unable to load uom : " + statusId);
            }
        }

        return string;
    }

    public static String getInventoryTypeFromDesc(String reasonDsc) throws Exception {
        for (Map.Entry<String, String> entry : getInstance().valueMap.entrySet()) {
            if (entry.getValue().equals(reasonDsc)) {
                return entry.getKey();
            }
        }


        throw new Exception("unable to find return reason : " + reasonDsc);
    }

    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        RequirmentMethodEnumSingleton.singletonSesion = singletonSesion;
    }
}
