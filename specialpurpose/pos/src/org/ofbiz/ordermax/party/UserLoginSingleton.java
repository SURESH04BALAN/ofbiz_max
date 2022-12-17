/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.party;

import org.ofbiz.ordermax.product.*;
import org.ofbiz.ordermax.party.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ListModel;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.UtilValidate;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.UserLogin;


/**
 *
 * @author siranjeev
 */
public class UserLoginSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, UserLogin> valueMap = null;

    private UserLoginSingleton() {
        valueMap = new HashMap<String, UserLogin>();
    }

    private static class SingletonHolder {

        public static final UserLoginSingleton INSTANCE = new UserLoginSingleton();
    }

    public static UserLoginSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }

    final static public List<UserLogin> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        
        return new ArrayList<UserLogin>(getInstance().valueMap.values());
    }

    final static public ListModel<UserLogin> getValueListModal() {
        ListAdapterListModel<UserLogin> modal = new ListAdapterListModel<UserLogin>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            Map<String, Object> whereClauseMap = new HashMap<String, Object>();
//            whereClauseMap.put("contactMechTypeId", contactMechTypeId);
            List<GenericValue> valueList = PosProductHelper.getGenericValueListsWithSelection("UserLogin", whereClauseMap, null, ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                UserLogin  partyType = new UserLogin(val);
     
                getInstance().valueMap.put(partyType.getuserLoginId(), partyType);
            }
        } catch (Exception ex) {
            Logger.getLogger(UserLoginSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static UserLogin getUserLogin(String userLoginId) throws Exception {
        UserLogin geoList = null;
        if (getInstance().valueMap.containsKey(userLoginId)) {
            geoList = getInstance().valueMap.get(userLoginId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(userLoginId)) {
                geoList = getInstance().valueMap.get(userLoginId);
            } else {
                throw new Exception("unable to load geo : " + userLoginId);
            }
        }

        return geoList;
    }

    public static UserLogin getUserLoginFromPartyId(String partyId) throws Exception {
        UserLogin userLogin = null;
        if(getInstance().valueMap.isEmpty()){
            loadAll();
        }
        
        for (Map.Entry<String, UserLogin> val : getInstance().valueMap.entrySet()) {
           if(UtilValidate.isNotEmpty(val.getValue().getpartyId()) && val.getValue().getpartyId().equals(partyId)){
               userLogin = val.getValue();
               break;
           }
       }

        return userLogin;
    }

    private static XuiSession singletonSession = null;

    public static XuiSession getSingletonSession() {
        return singletonSession;
    }

    public static void setSingletonSession(XuiSession singletonSession) {
        UserLoginSingleton.singletonSession = singletonSession;
    }
}
