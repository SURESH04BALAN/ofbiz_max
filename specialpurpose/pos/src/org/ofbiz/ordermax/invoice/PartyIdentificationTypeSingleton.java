/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.ofbiz.ordermax.invoice;

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
import org.ofbiz.ordermax.base.ControllerOptions;
import org.ofbiz.ordermax.base.PosProductHelper;
import org.ofbiz.ordermax.entity.PartyIdentificationType;
import org.ofbiz.ordermax.payment.PaymentTypeSingleton;

/**
 *
 * @author siranjeev
 */
public class PartyIdentificationTypeSingleton implements Serializable {

    private static final long serialVersionUID = 1L;
    Map<String, PartyIdentificationType> valueMap = null;
    private PartyIdentificationTypeSingleton() {
        valueMap = new HashMap<String, PartyIdentificationType>();
    }

    private static class SingletonHolder {
        
        public static final PartyIdentificationTypeSingleton INSTANCE = new PartyIdentificationTypeSingleton(); 
    }

    public static PartyIdentificationTypeSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }

    protected Object readResolve() {
        return getInstance();
    }
    final static public List<PartyIdentificationType> getValueList() {
        if (getInstance().valueMap.isEmpty()) {
            loadAll();
        }
        return new ArrayList<PartyIdentificationType>(getInstance().valueMap.values());
    }

    final static public ListModel<PartyIdentificationType> getValueListModal() {
        ListAdapterListModel<PartyIdentificationType> modal = new ListAdapterListModel<PartyIdentificationType>();
        modal.addAll(getInstance().getValueList());
        return modal;
    }

    static protected void loadAll() {
        try {
            getInstance().valueMap.clear();
            List<GenericValue> valueList = PosProductHelper.getGenericValueLists("PartyIdentificationType", ControllerOptions.getSession().getDelegator());
            for (GenericValue val : valueList) {
                PartyIdentificationType partyIdentificationType = new PartyIdentificationType(val);
                getInstance().valueMap.put(partyIdentificationType.getpartyIdentificationTypeId(), partyIdentificationType);
            }
        } catch (Exception ex) {
            Logger.getLogger(PaymentTypeSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    public static PartyIdentificationType getPartyIdentificationType(String partyIdentificationTypeId) throws Exception {
        PartyIdentificationType partyIdentificationType = null;
        if (getInstance().valueMap.containsKey(partyIdentificationTypeId)) {
            partyIdentificationType = getInstance().valueMap.get(partyIdentificationTypeId);
        } else {
            loadAll();
            if (getInstance().valueMap.containsKey(partyIdentificationTypeId)) {
                partyIdentificationType = getInstance().valueMap.get(partyIdentificationTypeId);
            } else {
                throw new Exception("unable to load paymentType : " + partyIdentificationTypeId);
            }
        }
        
        return partyIdentificationType;
    }


}
