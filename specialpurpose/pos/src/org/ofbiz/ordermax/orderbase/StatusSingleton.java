/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.orderbase;

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
import org.ofbiz.ordermax.entity.StatusItem;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;

/**
 *
 * @author siranjeev
 */
public class StatusSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, StatusItem> valueMap = null;

    private StatusSingleton() {
        valueMap = new HashMap<String, StatusItem>();
    }

    private static class OrderStatusSingletonHolder {

        public static final StatusSingleton INSTANCE = new StatusSingleton();
    }

    public static StatusSingleton getInstance() {
        return OrderStatusSingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<StatusItem> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        List<StatusItem> list = new ArrayList<StatusItem>(getInstance().valueMap.values());
        Collections.sort(list, new StatusItemComparator());
        return list;

    }

    final static public ListModel<StatusItem> getValueListModal() {
        ListAdapterListModel<StatusItem> modal = new ListAdapterListModel<StatusItem>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    final static public ListModel<StatusItem> getValueListModal(String typeId) {

        ListModel<StatusItem> modalRet = null;
        if (typeId != null) {
            if (getInstance().valueMap.isEmpty()) {
                loadAll();
            }

            ListAdapterListModel<StatusItem> modal = new ListAdapterListModel<StatusItem>();
            for (Map.Entry<String, StatusItem> entry : getInstance().valueMap.entrySet()) {
                if (entry.getValue().getstatusTypeId().equals(typeId)) {
                    modal.add(entry.getValue());
                }
            }
            modalRet = modal;
        } else {
            modalRet = getValueListModal();
        }

        return modalRet;
    }

    final static public List<StatusItem> getValueList(String typeId) {

        List<StatusItem> modalRet = null;
        if (typeId != null) {
            if (getInstance().valueMap.isEmpty()) {
                loadAll();
            }

            List<StatusItem> modal = new ArrayList<StatusItem>();
            for (Map.Entry<String, StatusItem> entry : getInstance().valueMap.entrySet()) {
                if (entry.getValue().getstatusTypeId().equals(typeId)) {
                    modal.add(entry.getValue());
                }
            }

            List<StatusItem> list = new ArrayList<StatusItem>(modal);
            Collections.sort(list, new StatusItemComparator());
            modalRet = list;
        } else {
            modalRet = getValueList();
        }

        return modalRet;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();

            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("StatusItem", ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                StatusItem statusItem = new StatusItem(val);
                getInstance().valueMap.put(statusItem.getstatusId(), statusItem);
            }
        } catch (Exception ex) {
            Logger.getLogger(PaymentTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static StatusItem getStatusItem(String statusId) throws Exception {
        /*       StatusItem statusItem = null;
         if (getInstance().valueMap.containsKey(statusId)) {
         statusItem = getInstance().valueMap.get(statusId);
         } else {
         try {
         List<GenericValue> valueList = PosProductHelper.getGenericValueLists("StatusItem", StatusSingleton.singletonSesion.getDelegator());
         for (GenericValue val : valueList) {
         statusItem = new StatusItem(val);
         getInstance().valueMap.put(statusItem.getstatusId(), statusItem);
         }
         } catch (Exception ex) {
         Logger.getLogger(StatusSingleton.class.getName()).log(Level.SEVERE, null, ex);
         throw new Exception("unable to load party: " + statusId);
         }
         }
         */
        StatusItem statusItem = null;
        if (getInstance().valueMap.containsKey(statusId)) {
            statusItem = getInstance().valueMap.get(statusId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(statusId)) {
                statusItem = getInstance().valueMap.get(statusId);
            } else {
                throw new Exception("unable to load uom : " + statusId);
            }
        }

        return statusItem;
    }

    public static StatusItem getReturnStatusFromDesc(String statusType, String statusDesc) throws Exception {
        List<StatusItem> valList = getValueList(statusType);
        for (StatusItem reason : valList) {
            if (reason.getdescription().equals(statusDesc)) {
                return reason;
            }
        }

        throw new Exception("unable to find status desc : " + statusDesc);
    }

    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        StatusSingleton.singletonSesion = singletonSesion;
    }

    protected static class StatusItemComparator implements Comparator<StatusItem> {

        @Override
        public int compare(StatusItem t, StatusItem t1) {
            return t.getdescription().compareTo(t1.getdescription());
//            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
