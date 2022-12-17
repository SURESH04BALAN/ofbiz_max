/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.party;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.controller.LoadAccountWorker;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Account;

/**
 *
 * @author siranjeev
 */

public class ContactMechListSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, Account> partyMap = null;
    private ContactMechListSingleton() {
        partyMap = new HashMap<String, Account>();
    }

    private static class PartyListSingletonHolder {
        
        public static final ContactMechListSingleton INSTANCE = new ContactMechListSingleton(); 
    }

    public static ContactMechListSingleton getInstance() {
        return PartyListSingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }
    
    public static Account getAccount(String partyId) throws Exception {
        Account account = null;
        if(getInstance().partyMap.containsKey(partyId)){
            account = getInstance().partyMap.get(partyId);
        }
        else{
            try {
                GenericValue partyGenValue = PosProductHelper.getParty(partyId, ControllerOptions.getSession().getDelegator());
                account = LoadAccountWorker.getAccount(partyId, ControllerOptions.getSession());
                getInstance().partyMap.put(partyId, account);
            } catch (Exception ex) {
                Logger.getLogger(ContactMechListSingleton.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("unable to load party: " + partyId);
            }
        }
        
        return account;
    }

    private static XuiSession singletonSesion = null;

    public static XuiSession getSingletonSesion() {
        return singletonSesion;
    }

    public static void setSingletonSesion(XuiSession singletonSesion) {
        ContactMechListSingleton.singletonSesion = singletonSesion;
    }    
}
