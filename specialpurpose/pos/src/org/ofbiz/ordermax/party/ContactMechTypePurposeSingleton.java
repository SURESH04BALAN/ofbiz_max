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
public class ContactMechTypePurposeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, List<ContactMechPurposeType>> valueMap = null;

    private ContactMechTypePurposeSingleton() {
        valueMap = new HashMap<String, List<ContactMechPurposeType>>();
    }

    private static class SingletonHolder {

        public static final ContactMechTypePurposeSingleton INSTANCE = new ContactMechTypePurposeSingleton();
    }

    public static ContactMechTypePurposeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<ContactMechPurposeType> getValueList(String contactMechTypeId) {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<ContactMechPurposeType>(getInstance().valueMap.get(contactMechTypeId));
    }

    final static public ListModel<ContactMechPurposeType> getValueListModal(String countryId) {
        ListAdapterListModel<ContactMechPurposeType> modal = new ListAdapterListModel<ContactMechPurposeType>();
        modal.addAll(getInstance().getValueList(countryId));
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("ContactMechTypePurpose", whereClauseMap, null, ContactMechTypePurposeSingleton.singletonSession.getDelegator());
            for (GenericValue val : valueList) {
                ContactMechPurposeType  contactMechPurposeType = ContactMechPurposeTypeSingleton.getContactMechPurposeType(val.getString("contactMechPurposeTypeId"));
                String purposeType = val.getString("contactMechTypeId");
                if(getInstance().valueMap.containsKey(purposeType)==false){
                   getInstance().valueMap.put(purposeType, new ArrayList<ContactMechPurposeType>());
                }
                
                getInstance().valueMap.get(purposeType).add(contactMechPurposeType);
            }
        } catch (Exception ex) {
            Logger.getLogger(ContactMechTypePurposeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static List<ContactMechPurposeType> getAssociatedRegions(String countryGeoId) throws Exception {
        List<ContactMechPurposeType> geoList = null;
        if (getInstance().valueMap.containsKey(countryGeoId)) {
            geoList = getInstance().valueMap.get(countryGeoId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(countryGeoId)) {
                geoList = getInstance().valueMap.get(countryGeoId);
            } else {
                throw new Exception("unable to load geo : " + countryGeoId);
            }
        }

        return geoList;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        ContactMechTypePurposeSingleton.singletonSession = singletonSession;
    }
}
