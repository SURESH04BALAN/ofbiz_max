/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.party;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mvc.controller.LoadAccountWorker;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.guiapp.xui.XuiContainer;
import org.ofbiz.guiapp.xui.XuiSession;
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.composite.Account;

/**
 *
 * @author siranjeev
 */

public class PartyListSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, Account> partyMap = null;
    private PartyListSingleton() {
        partyMap = new HashMap<String, Account>();
    }

    private static class PartyListSingletonHolder {
        
        public static final PartyListSingleton INSTANCE = new PartyListSingleton(); 
    }

    public static PartyListSingleton getInstance() {
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
                Logger.getLogger(PartyListSingleton.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception(ex.getMessage());
            }
        }
        
        return account;
    }

    final static public List<Account> getSupplierValueList() {
        List<Account> modal = new ArrayList<Account>();
        List<GenericValue> supplierList = LoadAccountWorker.getPartyByRole("SUPPLIER", ControllerOptions.getSession());
        for(GenericValue roleType : supplierList){
            String id = roleType.getString("partyId");
            Account account;
            try {
                account = PartyListSingleton.getAccount(id);
                modal.add(account);                            
            } catch (Exception ex) {
                Logger.getLogger(PartyListSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return modal;
    }

    final static public List<Account> getCustomerValueList() {
        List<Account> modal = new ArrayList<Account>();
        List<GenericValue> supplierList = LoadAccountWorker.getPartyByRole("CUSTOMER", XuiContainer.getSession());
        for(GenericValue roleType : supplierList){
            String id = roleType.getString("partyId");
            Account account;
            try {
                account = PartyListSingleton.getAccount(id);
                modal.add(account);                            
            } catch (Exception ex) {
                Logger.getLogger(PartyListSingleton.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return modal;
    }

 
}
