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
import org.ofbiz.base.util.Debug;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.Uom;

/**
 *
 * @author siranjeev
 */
public class UOMSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, Uom> valueMap = null;

    private UOMSingleton() {
        valueMap = new HashMap<String, Uom>();
    }

    private static class SingletonHolder {

        public static final UOMSingleton INSTANCE = new UOMSingleton();
    }

    public static UOMSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    final static public List<Uom> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }

        return new ArrayList<Uom>(getInstance().valueMap.values());
    }

    final static public List<Uom> getValueList(String typeId) {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        List<Uom> list = new ArrayList<Uom>();
        
        if (typeId != null) {
            for (Map.Entry<String, Uom> entry : getInstance().valueMap.entrySet()) {
                if (entry.getValue().getuomTypeId().equals(typeId)) {
                    list.add(entry.getValue());
                }
            }
        } else {
            list = getValueList();
        }
        return list;
    }

    final static public ListModel<Uom> getValueListModal() {
        ListAdapterListModel<Uom> modal = new ListAdapterListModel<Uom>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    final static public ListModel<Uom> getValueListModal(String typeId) {
        Debug.logInfo(" typeId : " + typeId, "module");
        ListModel<Uom> modalRet = null;
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }

        if (typeId != null) {
            ListAdapterListModel<Uom> modal = new ListAdapterListModel<Uom>();
            for (Map.Entry<String, Uom> entry : getInstance().valueMap.entrySet()) {
                if (entry.getValue().getuomTypeId().equals(typeId)) {
                    modal.add(entry.getValue());
                }
            }
            modalRet = modal;
        } else {
            modalRet = getValueListModal();
        }

        return modalRet;
    }

    protected Object readResolve() {
        return getInstance();
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();

            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("Uom", ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                Uom uom = new Uom(val);
                getInstance().valueMap.put(uom.getuomId(), uom);
            }
        } catch (Exception ex) {
            Logger.getLogger(PaymentTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Uom getUom(String uomId) throws Exception {
        Uom paymentType = null;
        if (getInstance().valueMap.containsKey(uomId)) {
            paymentType = getInstance().valueMap.get(uomId);
        } else {

            loadAll();
            if (getInstance().valueMap.containsKey(uomId)) {
                paymentType = getInstance().valueMap.get(uomId);
            } else {
                throw new Exception("unable to load uom : " + uomId);
            }
        }

        return paymentType;
    }

    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        UOMSingleton.singletonSesion = singletonSesion;
    }
}
