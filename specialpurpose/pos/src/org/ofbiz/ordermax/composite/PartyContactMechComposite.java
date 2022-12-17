/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ofbiz.ordermax.composite;

import org.ofbiz.base.util.Debug;
import static org.ofbiz.ordermax.composite.Contact.module;
import org.ofbiz.ordermax.entity.PartyContactMech;

/**
 *
 * @author siranjeev
 */
public class PartyContactMechComposite {

    private PartyContactMech partyContactMech;
    private Contact contact;    
    PartyContactMechPurposeList partyContactMechPurposeList = new PartyContactMechPurposeList();

    public PartyContactMechComposite() {

    }

    public PartyContactMech getPartyContactMech() {
        return partyContactMech;
    }

    public void setPartyContactMech(PartyContactMech partyContactMech) {
        this.partyContactMech = partyContactMech;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public PartyContactMechPurposeList getPartyContactMechPurposeList() {
        return partyContactMechPurposeList;
    }

    public void setPartyContactMechPurposeList(PartyContactMechPurposeList partyContactMechPurposeList) {
        this.partyContactMechPurposeList = partyContactMechPurposeList;
    }

    public boolean isPartyContactMechPurposeExists(String purposeId) {

        PartyContactMechPurposeComposite pc = null;
        for (PartyContactMechPurposeComposite itr : partyContactMechPurposeList.getList()) {
            if (itr.getPartyContactMechPurpose().getcontactMechPurposeTypeId().equals(purposeId)) {
                return true;
            }
        }

        return false;
    }
    
    public void setContachMechId(String contachMechId){
        partyContactMech.setcontactMechId(contachMechId);
        contact.setContachMechId(contachMechId);    
       PartyContactMechPurposeComposite pc = null;
        for (PartyContactMechPurposeComposite itr : partyContactMechPurposeList.getList()) {
            itr.getPartyContactMechPurpose().setcontactMechId(contachMechId);
        }        
    }
    
    public void outputToDebug() {
        Debug.logInfo("partyContactMech Id: " + partyContactMech.getcontactMechId(), module);
        Debug.logInfo("partyContactPurpos Id: " + contact.getContactMechTypeId(), module);
        Debug.logInfo("partyContactMech: " + partyContactMech.getpartyId(), module);
        contact.outputToDebug();
    }
}
