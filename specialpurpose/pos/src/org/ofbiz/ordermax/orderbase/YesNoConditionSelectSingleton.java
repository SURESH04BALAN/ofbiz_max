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

/**
 *
 * @author siranjeev
 */
public class YesNoConditionSelectSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, String> valueMap = null;

    private YesNoConditionSelectSingleton() {
        valueMap = new HashMap<String, String>();
    }

    private static class SingletonHolder {

        public static final YesNoConditionSelectSingleton INSTANCE = new YesNoConditionSelectSingleton();
    }

    public static YesNoConditionSelectSingleton getInstance() {
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
    static public final String Y = "Yes";            
    static public final String N = "No";
    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
    
            getInstance().valueMap.put("Y", Y);
            getInstance().valueMap.put("N", N);

        } catch (Exception ex) {
            Logger.getLogger(YesNoConditionSelectSingleton.class.getName()).log(Level.SEVERE, null, ex);
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
    public static String getKeyFromDisplayName(String name) throws Exception {
        String string = null;
        for (Map.Entry<String, String> entry : getInstance().valueMap.entrySet()) {
                if (entry.getValue().equals(name)) {
                  return entry.getKey();
                }
            }
       
        return string;
    }    
    
        public static String setComboValue(String name) throws Exception {
        String string = null;
        for (Map.Entry<String, String> entry : getInstance().valueMap.entrySet()) {
                if (entry.getValue().equals(name)) {
                  return entry.getKey();
                }
            }
       
        return string;
    } 
}
