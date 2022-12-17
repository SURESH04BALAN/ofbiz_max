/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

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
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.ContactMechPurposeType;

/**
 *
 * @author siranjeev
 */
public class ContactMechPurposeTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, ContactMechPurposeType> valueMap = null;

    private ContactMechPurposeTypeSingleton() {
        valueMap = new HashMap<String, ContactMechPurposeType>();
    }

    private static class SingletonHolder {

        public static final ContactMechPurposeTypeSingleton INSTANCE = new ContactMechPurposeTypeSingleton();
    }

    public static ContactMechPurposeTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ContactMechPurposeType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        return new ArrayList<ContactMechPurposeType>(getInstance().valueMap.values());
    }

    final static public ListModel<ContactMechPurposeType> getValueListModal() {
        ListAdapterListModel<ContactMechPurposeType> modal = new ListAdapterListModel<ContactMechPurposeType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("ContactMechPurposeType", ContactMechPurposeTypeSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                ContactMechPurposeType contactMechPurposeType = new ContactMechPurposeType(val);
                getInstance().valueMap.put(contactMechPurposeType.getcontactMechPurposeTypeId(), contactMechPurposeType);
            }
        } catch (Exception ex) {
            Logger.getLogger(ContactMechPurposeTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static ContactMechPurposeType getContactMechPurposeType(String geoId) throws Exception {
        ContactMechPurposeType contactMechPurposeType = null;
        if (getInstance().valueMap.containsKey(geoId)) {
            contactMechPurposeType = getInstance().valueMap.get(geoId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(geoId)) {
                contactMechPurposeType = getInstance().valueMap.get(geoId);
            } else {
                throw new Exception("unable to load contactMechPurposeType : " + geoId);
            }
        }

        return contactMechPurposeType;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        ContactMechPurposeTypeSingleton.singletonSession = singletonSession;
    }
}
