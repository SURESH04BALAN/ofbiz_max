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
public class PercentageSelectSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<Integer, String> valueMap = null;

    private PercentageSelectSingleton() {
        valueMap = new HashMap<Integer, String>();
    }

    private static class SingletonHolder {

        public static final PercentageSelectSingleton INSTANCE = new PercentageSelectSingleton();
    }

    public static PercentageSelectSingleton getInstance() {
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

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            for (int i = 0; i <= 100; i++) {
                getInstance().valueMap.put(i, String.valueOf(i) + "%");
            }

        } catch (Exception ex) {
            Logger.getLogger(PercentageSelectSingleton.class.getName()).log(Level.SEVERE, null, ex);
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
}
