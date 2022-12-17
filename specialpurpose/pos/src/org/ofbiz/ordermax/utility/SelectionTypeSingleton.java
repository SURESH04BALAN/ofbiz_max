/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.utility;

import org.ofbiz.ordermax.payment.*;
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
import org.ofbiz.base.util.UtilProperties;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.RoleType;

/**
 *
 * @author siranjeev
 */
public class SelectionTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, String> valueMap = null;

    private SelectionTypeSingleton() {
        valueMap = new HashMap<String, String>();
    }

    private static class SingletonHolder {

        public static final SelectionTypeSingleton INSTANCE = new SelectionTypeSingleton();
    }

    public static SelectionTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
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

    protected Object readResolve() {
        return getInstance();
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            //Locale locale = (Locale) context.get("locale");
            Locale locale = Locale.getDefault();
            String opEquals = UtilProperties.getMessage("conditional", "equals", locale);
            String opBeginsWith = UtilProperties.getMessage("conditional", "begins_with", locale);
            String opContains = UtilProperties.getMessage("conditional", "contains", locale);
            String opIsEmpty = UtilProperties.getMessage("conditional", "is_empty", locale);
            String opNotEqual = UtilProperties.getMessage("conditional", "not_equal", locale);

            getInstance().valueMap.put("equals", opEquals);
            getInstance().valueMap.put("begins_with", opBeginsWith);
            getInstance().valueMap.put("contains", opContains);
            getInstance().valueMap.put("is_empty", opIsEmpty);            
            getInstance().valueMap.put("not_equal", opNotEqual);                        

        } catch (Exception ex) {
            Logger.getLogger(PaymentTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getConditionName(String roleTypeId) throws Exception {
        String paymentType = null;
        if (getInstance().valueMap.containsKey(roleTypeId)) {
            paymentType = getInstance().valueMap.get(roleTypeId);
        } else {

            loadAll();
            if (getInstance().valueMap.containsKey(roleTypeId)) {
                paymentType = getInstance().valueMap.get(roleTypeId);
            } else {
                throw new Exception("unable to load condition : " + roleTypeId);
            }
        }

        return paymentType;
    }

    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        SelectionTypeSingleton.singletonSesion = singletonSesion;
    }
}
