/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

import java.util.ArrayList;
import java.util.List;
import static javax.swing.Action.NAME;
import mvc.model.list.ListAdapterListModel;
import org.ofbiz.base.util.Debug;

/**
 *
 * @author siranjeev
 */
public class PartyContactMechCompositeList extends ListAdapterListModel<PartyContactMechComposite> {

    public PartyContactMechCompositeList() {

    }

    public PartyContactMechComposite getBillingLocationContact() {
        PartyContactMechComposite oc = getPartyContact("BILLING_LOCATION");
        return oc;
    }

    public PartyContactMechComposite getBillingLocationPhoneContact() {
        PartyContactMechComposite oc = getPartyContact("PHONE_BILLING");
        return oc;
    }

    public PartyContactMechComposite getShippingLocationContact() {
        PartyContactMechComposite oc = getPartyContact("SHIPPING_LOCATION");
        return oc;
    }

    public PartyContactMechComposite getShippingLocationPhoneContact() {
        PartyContactMechComposite oc = getPartyContact("PHONE_SHIPPING");
        return oc;
    }

    public PartyContactMechComposite getPartyContact(String contactMechPurposeId) {

        PartyContactMechComposite pc = null;
        for (PartyContactMechComposite itr : list) {
            Debug.logInfo("ocm.getcontactMechId() " + itr.getContact().getContactMech().getcontactMechId(), "module");
            if (itr.isPartyContactMechPurposeExists(contactMechPurposeId)) {
                pc = itr;
                break;
            }
        }

        return pc;
    }

        public PartyContactMechComposite getPartyContactMechComposite(String contactMechId) {

        PartyContactMechComposite pc = null;
        for (PartyContactMechComposite itr : list) {
            Debug.logInfo("ocm.getcontactMechId() " + itr.getContact().getContactMech().getcontactMechId(), "module");
            if (itr.getContact().getContactMech().getcontactMechId().equals(contactMechId)) {
                pc = itr;
                break;
            }
        }

        return pc;
    }
        
    public List<PartyContactMechComposite> getPartyContactMechTypeList(String contachMechTypeId) {

        ArrayList<PartyContactMechComposite> pcList = new ArrayList<PartyContactMechComposite>();

        for (PartyContactMechComposite itr : list) {
            if (itr.getContact().getContactMech().getcontactMechTypeId().equals(contachMechTypeId)) {
                pcList.add(itr);
            }
        }

        return pcList;
    }

    public PartyContactMechComposite getPartyContactMech(String contachMechId) {

        ArrayList<PartyContactMechComposite> pcList = new ArrayList<PartyContactMechComposite>();

        for (PartyContactMechComposite itr : list) {
            if (itr.getContact().getContactMech().getcontactMechId().equals(contachMechId)) {
                return itr;
            }
        }

        return null;
    }

    public void outputToDebug() {
        Debug.logInfo("getSize(): " + getSize(), NAME);
        for (int i = 0; i < getSize(); ++i) {
            PartyContactMechComposite contact = getElementAt(i);
            contact.outputToDebug();

        }
    }
}
